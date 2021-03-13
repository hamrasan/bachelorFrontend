package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.HumidityDto;
import cz.fel.cvut.hamrasan.gardener.dto.PressureDto;
import cz.fel.cvut.hamrasan.gardener.dto.TemperatureDto;
import cz.fel.cvut.hamrasan.gardener.service.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class RpcController {

    private RpcService rpcService;

    @Autowired
    public RpcController(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    @GetMapping(value = "/temperature", produces = MediaType.APPLICATION_JSON_VALUE )
    public TemperatureDto getTemperature() {
        return rpcService.getLatestTemperature();
    }

    @GetMapping(value = "/humidity", produces = MediaType.APPLICATION_JSON_VALUE )
    public HumidityDto getHumidity() {
        return rpcService.getLatestHumidity();
    }

    @GetMapping(value = "/pressure", produces = MediaType.APPLICATION_JSON_VALUE )
    public PressureDto getPressure() {
        return rpcService.getLatestPressure();
    }
}
