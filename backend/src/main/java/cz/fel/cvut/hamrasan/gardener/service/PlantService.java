package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.PlantDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dto.PlantDto;
import cz.fel.cvut.hamrasan.gardener.model.Garden;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantService {

    private PlantDao plantDao;
    private UserDao userDao;
    private TranslateService translateService;


    @Autowired
    public PlantService(PlantDao plantDao, UserDao userDao, TranslateService translateService) {
        this.plantDao = plantDao;
        this.userDao = userDao;
        this.translateService = translateService;
    }

    @Transactional
    public List<PlantDto> findAll() {
        List<PlantDto> dtos = new ArrayList<PlantDto>();

        for (Plant plant: plantDao.findAll()) {
            dtos.add(translateService.translatePlant(plant));
        }
        return dtos;
    }

    @Transactional
    public PlantDto find(Long id) {
        return translateService.translatePlant(plantDao.find(id));
    }

    @Transactional
    public PlantDto find(String name) {
        return translateService.translatePlant(plantDao.findByName(name));
    }

    @Transactional
    public List<PlantDto> getUserPlants(){
        List<PlantDto> dtos = new ArrayList<PlantDto>();

        for (Garden garden:userDao.find(SecurityUtils.getCurrentUser().getId()).getGardens()) {
            for (Plant plant: garden.getPlants()) {
                dtos.add(translateService.translatePlant(plant));
            }
        }
        return dtos;
    }
}
