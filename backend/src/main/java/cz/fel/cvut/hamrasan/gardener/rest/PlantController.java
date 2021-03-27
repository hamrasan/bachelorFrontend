package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.PlantDto;
import cz.fel.cvut.hamrasan.gardener.dto.PlantWithoutDateDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.MissingVariableException;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plants")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class PlantController {

    private PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<PlantWithoutDateDto> getAll() {
        return plantService.findAll();
    }

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE )
    public List<PlantDto> getAllOfUSer(){
        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return plantService.getUserPlants();
        }
        else return null;
    }

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PlantWithoutDateDto getPlant(@PathVariable Long id) {
        return plantService.find(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PlantDto getUserPlant(@PathVariable Long id) {
        return plantService.findUserPlant(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPlant(@RequestBody Plant plant)throws MissingVariableException {
        plantService.create(plant);
    }
}
