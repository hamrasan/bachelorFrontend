package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.GardenDto;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/garden")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class GardenController {

    private GardenService gardenService;

    @Autowired
    public GardenController(GardenService gardenService) {

        this.gardenService = gardenService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<GardenDto> getAll() {
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return gardenService.findAllOfUser();
        }
        return new ArrayList<>();
    }
}
