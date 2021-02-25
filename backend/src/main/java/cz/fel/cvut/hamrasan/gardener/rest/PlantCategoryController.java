package cz.fel.cvut.hamrasan.gardener.rest;

import cz.fel.cvut.hamrasan.gardener.dto.PlantCategoryDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.service.PlantCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
public class PlantCategoryController {

    private PlantCategoryService categoryService;

    @Autowired
    public PlantCategoryController(PlantCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlantCategoryDto> getAll(){
        return categoryService.findAll();
    }

    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlantCategoryDto getPlantCategory(@PathVariable Long id ) throws NotFoundException {
        return categoryService.find(id);
    }
}
