package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dao.ValveDao;
import cz.fel.cvut.hamrasan.gardener.dao.ValveScheduleDao;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.model.Valve;
import cz.fel.cvut.hamrasan.gardener.model.ValveSchedule;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

@Service
public class ScheduleService {

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private ValveDao valveDao;
    private ValveService valveService;
    private ValveScheduleDao valveScheduleDao;
    private UserDao userDao;

    @Autowired
    public ScheduleService(ValveDao valveDao, ValveService valveService, ValveScheduleDao valveScheduleDao, UserDao userDao) {

        this.valveDao = valveDao;
        this.valveService = valveService;
        this.valveScheduleDao = valveScheduleDao;
        this.userDao = userDao;
    }


    @Scheduled(cron = "0 44 22 * * *", zone = "CET")
    public void scheduler() {

        System.out.println("Hello");
        for (Valve valve : valveDao.findAll()) {
            System.out.println(valve.getName());

            for (ValveSchedule valveSchedule: valveScheduleDao.findByValve(valve.getId())) {
                final Runnable valving = new Runnable() {
                    public void run() {
                        System.out.println(valveSchedule.getHour());
                        try {
                            valveService.moveValve(valve.getName(), "true");
                            valveService.setStopValving(valve.getName(), valveSchedule.getLength());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                final ScheduledFuture<?> valveHandle = scheduler.schedule(valving, (valveSchedule.getHour()- LocalDateTime.now().getHour())+ (valveSchedule.getMinutes() - LocalDateTime.now().getMinute()), MINUTES);

//                final ScheduledFuture<?> valveHandle = scheduler.schedule(valving, (valveSchedule.getHour()*60) + valveSchedule.getMinutes(), MINUTES);
//
//                scheduler.schedule(new Runnable() {
//
//                    public void run() { beeperHandle.cancel(true); }
//                }, 60 * 60, SECONDS);
            }
        }
    }

    public void setSchedule(String nameValve, List<Integer> days, String time, Integer valvingLength) throws NotAllowedException {
        Valve valve = valveDao.findByName(nameValve);
        User user = userDao.find(SecurityUtils.getCurrentUser().getId());
        System.out.println(user.getValves());
        String[] arrOfStr = time.split(":");
        if(!user.getValves().contains(valve)) throw new NotAllowedException("Not allowed operation");
        ValveSchedule valveSchedule = new ValveSchedule(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), valvingLength, valve, days);
        valveScheduleDao.persist(valveSchedule);
    }
}
