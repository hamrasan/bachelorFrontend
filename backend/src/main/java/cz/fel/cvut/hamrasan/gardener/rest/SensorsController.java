package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.amqp.rpc.Tut6Client;
import cz.fel.cvut.hamrasan.gardener.dto.*;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class SensorsController {

    private SensorsService sensorsService;

    @Autowired
    public SensorsController(SensorsService sensorsService) {

        this.sensorsService = sensorsService;
    }

    @GetMapping(value = "/temperature/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public TemperatureDto getTemperature(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return sensorsService.getLatestTemperature(gardenId);
    }

    @GetMapping(value = "/humidity/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public HumidityDto getHumidity(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return sensorsService.getLatestHumidity(gardenId);
    }

    @GetMapping(value = "/pressure/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PressureDto getPressure(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return sensorsService.getLatestPressure(gardenId);
    }

    @GetMapping(value = "/request/{minutes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setRequestData(@PathVariable String minutes) {
        sensorsService.setMeassureMinutes(minutes);
    }

    @GetMapping(value = "/rain/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public RainDto getRain(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return sensorsService.getLatestRain(gardenId);
    }

    @GetMapping(value = "/soil/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public SoilDto getSoil(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return sensorsService.getLatestSoil(gardenId);
    }

    @GetMapping(value = "/history/rain/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RainDto> getHistoryRain(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return sensorsService.getHistoryRain(gardenId);
    }

    @GetMapping(value = "/history/temperature/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TemperatureDto> getHistoryTemperature(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return sensorsService.getHistoryTemperature(gardenId);
    }

    @GetMapping(value = "/history/pressure/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PressureDto> getHistoryPressure(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return sensorsService.getHistoryPressure(gardenId);
    }

    @GetMapping(value = "/history/humidity/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HumidityDto> getHistoryHumidity(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return sensorsService.getHistoryHumidity(gardenId);
    }

    @GetMapping(value = "/history/soil/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoilDto> getHistorySoil(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return sensorsService.getHistorySoil(gardenId);
    }
}
