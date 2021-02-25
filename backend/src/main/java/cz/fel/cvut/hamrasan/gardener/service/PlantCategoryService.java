package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.PlantCategoryDao;
import cz.fel.cvut.hamrasan.gardener.dto.PlantCategoryDto;
import cz.fel.cvut.hamrasan.gardener.model.PlantCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public
class PlantCategoryService {

    private PlantCategoryDao plantCategoryDao;
    private TranslateService translateService;


    @Autowired
    public PlantCategoryService(PlantCategoryDao plantCategoryDao, TranslateService translateService) {
        this.plantCategoryDao = plantCategoryDao;
        this.translateService = translateService;
    }

    @Transactional
    public List<PlantCategoryDto> findAll() {
        List<PlantCategoryDto> dtos = new ArrayList<PlantCategoryDto>();

        for (PlantCategory category: plantCategoryDao.findAll() ) {
            dtos.add(translateService.translatePlantCategory(category));
        }

        return dtos;
    }


    @Transactional
    public PlantCategoryDto find(Long id) {
        return translateService.translatePlantCategory(plantCategoryDao.find(id));
    }

}
