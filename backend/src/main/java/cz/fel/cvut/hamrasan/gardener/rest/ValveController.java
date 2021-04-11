package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.service.ValveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/valve")
public class ValveController {

    private ValveService valveService;

    @Autowired
    public ValveController(ValveService valveService) {
        this.valveService = valveService;
    }

    @GetMapping(value = "/config")
    public void getAll(@RequestHeader("Host") String host) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        System.out.println(host);
        valveService.setupApi();
    }
}
