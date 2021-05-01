package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.amqp.rpc.Tut6Client;
import cz.fel.cvut.hamrasan.gardener.dto.*;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class SensorsController {

    private RpcService rpcService;

    @Autowired
    public SensorsController(RpcService rpcService) {

        this.rpcService = rpcService;
    }

    @GetMapping(value = "/temperature/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public TemperatureDto getTemperature(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return rpcService.getLatestTemperature(gardenId);
    }

    @GetMapping(value = "/humidity/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public HumidityDto getHumidity(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return rpcService.getLatestHumidity(gardenId);
    }

    @GetMapping(value = "/pressure/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PressureDto getPressure(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return rpcService.getLatestPressure(gardenId);
    }

    @GetMapping(value = "/request/{minutes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setRequestData(@PathVariable String minutes) {
         rpcService.setMeassureMinutes(minutes);
    }

    @GetMapping(value = "/rain/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public RainDto getRain(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return rpcService.getLatestRain(gardenId);
    }

    @GetMapping(value = "/soil/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public SoilDto getSoil(@PathVariable Long gardenId) throws NotAllowedException {

        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
        return rpcService.getLatestSoil(gardenId);
    }

    @GetMapping(value = "/history/rain/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RainDto> getHistoryRain(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return rpcService.getHistoryRain(gardenId);
    }

    @GetMapping(value = "/history/temperature/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TemperatureDto> getHistoryTemperature(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return rpcService.getHistoryTemperature(gardenId);
    }

    @GetMapping(value = "/history/pressure/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PressureDto> getHistoryPressure(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return rpcService.getHistoryPressure(gardenId);
    }

    @GetMapping(value = "/history/humidity/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HumidityDto> getHistoryHumidity(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return rpcService.getHistoryHumidity(gardenId);
    }

    @GetMapping(value = "/history/soil/{gardenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SoilDto> getHistorySoil(@PathVariable Long gardenId) throws NotAllowedException {
        if (SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");

        return rpcService.getHistorySoil(gardenId);
    }
}
