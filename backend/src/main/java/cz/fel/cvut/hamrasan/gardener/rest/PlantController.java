package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.PlantDto;
import cz.fel.cvut.hamrasan.gardener.dto.PlantWithoutDateDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.MissingVariableException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.model.Garden;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.model.UserPlant;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import cz.fel.cvut.hamrasan.gardener.service.PlantService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
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
        else { return null; }
    }

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PlantWithoutDateDto getPlant(@PathVariable Long id) {
        return plantService.find(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public PlantDto getUserPlant(@PathVariable Long id) {

        if(!SecurityUtils.isAuthenticatedAnonymously()) {
            return plantService.findUserPlant(id);
        }
        else { return null; }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPlant(@RequestBody HashMap<String,String> hashMap)throws MissingVariableException {

//        System.out.println("DEBILNY DATUM " + date);
//        System.out.println("DEBILNY PLANT " + plant);
        LocalDate date = LocalDate.parse(hashMap.get("date").substring(0,10));
        Long plantId = Long.parseLong(hashMap.get("plant"));
        Long gardenId = Long.parseLong(hashMap.get("garden"));


        plantService.create(date,plantId,gardenId);
    }

    @GetMapping(value = "/{category}/{subcategory}", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<PlantWithoutDateDto> getAllOfSubcategory(@PathVariable String category, @PathVariable String subcategory) throws NotFoundException {
        return plantService.findAllOfSubcategory(category, subcategory);
    }
}
