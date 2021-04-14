package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.ValveDto;
import cz.fel.cvut.hamrasan.gardener.dto.ValveWithScheduleDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.ValveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/valve")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class ValveController {

    private ValveService valveService;

    @Autowired
    public ValveController(ValveService valveService) {
        this.valveService = valveService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValveDto> getAllOfUserRaw() throws NotFoundException {
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return valveService.getAllOfUserRaw();
        }
        else { return null; }
    }

    @GetMapping(value = "/allsc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValveWithScheduleDto> getAllOfUserFull() throws NotFoundException {
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return valveService.getAllOfUserFull();
        }
        else { return null; }
    }

    @PostMapping(value = "/create/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createValve(@PathVariable String name, @RequestBody HashMap<String,List<Integer>> hashMap){

        List<Integer> ids = hashMap.get("gardens");
        valveService.createValve(name, ids);
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
