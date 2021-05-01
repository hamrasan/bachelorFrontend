package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dao.ValveDao;
import cz.fel.cvut.hamrasan.gardener.dao.ValveScheduleDao;
import cz.fel.cvut.hamrasan.gardener.dto.ValveScheduleDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.model.Valve;
import cz.fel.cvut.hamrasan.gardener.model.ValveSchedule;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private TranslateService translateService;

    @Autowired
    public ScheduleService(ValveDao valveDao, ValveService valveService, ValveScheduleDao valveScheduleDao, UserDao userDao, TranslateService translateService) {

        this.valveDao = valveDao;
        this.valveService = valveService;
        this.valveScheduleDao = valveScheduleDao;
        this.userDao = userDao;
        this.translateService = translateService;
    }


    @Scheduled(cron = "0 0 0 * * *", zone = "CET")
    public void scheduler() {

        for (Valve valve : valveDao.findAll()) {
            System.out.println(valve.getName());

            for (ValveSchedule valveSchedule: valveScheduleDao.findByValve(valve.getId())) {
                final Runnable valving = new Runnable() {
                    public void run() {
                        System.out.println(valveSchedule.getHour());
                        if(valveScheduleDao.find(valveSchedule.getId()) != null) {
                            try {
                                valveService.moveValve(valve.getName(), "true");
                                valveService.setStopValving(valve.getName(), valveSchedule.getLength());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
//                final ScheduledFuture<?> valveHandle = scheduler.schedule(valving, (valveSchedule.getHour()- LocalDateTime.now().getHour())+ (valveSchedule.getMinutes() - LocalDateTime.now().getMinute()), MINUTES);
                Date date = new Date(System.currentTimeMillis());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                    //DAY must be +1 bcs Sunday is 1 in Calendar
                if(valveSchedule.getDays().contains(c.get(Calendar.DAY_OF_WEEK)+1)){
                    System.out.println(c.get(Calendar.DAY_OF_WEEK));
                    final ScheduledFuture<?> valveHandle = scheduler.schedule(valving, (valveSchedule.getHour()*60) + valveSchedule.getMinutes(), MINUTES);
                }
//                scheduler.schedule(new Runnable() {
//
//                    public void run() { beeperHandle.cancel(true); }
//                }, 60 * 60, SECONDS);
            }
        }
    }

    @Transactional
    public void setSchedule(String nameValve, List<Integer> days, String time, Integer valvingLength) throws NotAllowedException {
        Valve valve = valveDao.findByName(nameValve);
        User user = userDao.find(SecurityUtils.getCurrentUser().getId());
        System.out.println(user.getValves());
        String[] arrOfStr = time.split(":");
        System.out.println(user.getValves().contains(valve));

        if(!user.getValves().contains(valve)) throw new NotAllowedException("Not allowed operation");
        ValveSchedule valveSchedule = new ValveSchedule(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), valvingLength, valve, days);
        valveScheduleDao.persist(valveSchedule);
    }

    @Transactional
    public List<ValveScheduleDto> getUserSchedules(String valveName) throws NotAllowedException {
        User user = userDao.find(SecurityUtils.getCurrentUser().getId());
        Valve valve = valveDao.findByName(valveName);
        List<ValveScheduleDto> valveScheduleDtos = new ArrayList<>();
        if(!user.getValves().contains(valve)) throw new NotAllowedException("Not yours valve");

        for (ValveSchedule valveSchedule : valveScheduleDao.findByValve(valve.getId())) {
            valveScheduleDtos.add(translateService.translateValveSchedule(valveSchedule));
        }
        return valveScheduleDtos;
    }

    @Transactional
    public void deleteSchedule(Long id){
        ValveSchedule valveSchedule = valveScheduleDao.find(id);
        valveScheduleDao.remove(valveSchedule);

    }
}
