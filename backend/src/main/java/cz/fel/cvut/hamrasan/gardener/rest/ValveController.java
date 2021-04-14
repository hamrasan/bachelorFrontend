package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.service.ValveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@RestController
@RequestMapping("/valve")
public class ValveController {

    private ValveService valveService;

    @Autowired
    public ValveController(ValveService valveService) {
        this.valveService = valveService;
    }

    @GetMapping(value = "/config")
    public void configApi() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        valveService.setupApi();
    }

    @GetMapping(value = "/refresh")
    public void refreshApi() throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException {
        valveService.refreshApiToken();
    }

    @PostMapping(value = "/move")
    public void moveValve(@RequestBody HashMap<String,String> request) throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException {
        valveService.moveValve(request.get("deviceId"), request.get("onOffValve"));
    }
}
