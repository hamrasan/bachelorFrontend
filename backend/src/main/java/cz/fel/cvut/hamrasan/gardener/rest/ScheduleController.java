package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.RequestWrapperSchedule;
import cz.fel.cvut.hamrasan.gardener.dto.ValveScheduleDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {

        this.scheduleService = scheduleService;
    }

    @PostMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setSchedule(@RequestBody RequestWrapperSchedule requestWrapperSchedule, @PathVariable String name) throws NotAllowedException {
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            scheduleService.setSchedule(name, requestWrapperSchedule.getDays(), requestWrapperSchedule.getTime(), requestWrapperSchedule.getValvingLength());
        }
    }

    @GetMapping(value = "/all/{valveName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValveScheduleDto> getUserSchedules(@PathVariable String valveName) throws NotAllowedException {
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return scheduleService.getUserSchedules(valveName);
        }
        return null;
    }
}
