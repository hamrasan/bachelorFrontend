package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.PlantDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserPlantDao;
import cz.fel.cvut.hamrasan.gardener.dto.PlantDto;
import cz.fel.cvut.hamrasan.gardener.dto.PlantWithoutDateDto;
import cz.fel.cvut.hamrasan.gardener.model.Garden;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.model.UserPlant;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlantService {

    private PlantDao plantDao;
    private UserDao userDao;
    private TranslateService translateService;
    private UserPlantDao userPlantDao;

    @Autowired
    public PlantService(PlantDao plantDao, UserDao userDao, TranslateService translateService, UserPlantDao userPlantDao) {
        this.plantDao = plantDao;
        this.userDao = userDao;
        this.translateService = translateService;
        this.userPlantDao = userPlantDao;
    }

    @Transactional
    public List<PlantWithoutDateDto> findAll() {
        List<PlantWithoutDateDto> dtos = new ArrayList<PlantWithoutDateDto>();

        for (Plant plant: plantDao.findAll()) {
            dtos.add(translateService.translatePlant(plant));
        }
        return dtos;
    }

    @Transactional
    public PlantWithoutDateDto find(Long id) {
        return translateService.translatePlant(plantDao.find(id));
    }

    @Transactional
    public PlantDto findUserPlant(Long id) {
        return translateService.translateUserPlant(userPlantDao.find(id));
    }

    @Transactional
    public PlantDto findUserPlant(String name) {
        return translateService.translateUserPlant(userPlantDao.findByName(name));
    }

    @Transactional
    public PlantWithoutDateDto find(String name) {
        return translateService.translatePlant(plantDao.findByName(name));
    }

    @Transactional
    public List<PlantDto> getUserPlants(){
        List<PlantDto> dtos = new ArrayList<PlantDto>();

        for (Garden garden:userDao.find(SecurityUtils.getCurrentUser().getId()).getGardens()) {
            for (UserPlant userPlant: garden.getPlants()) {
                dtos.add(translateService.translateUserPlant(userPlant));
            }
        }
        return dtos;
    }

    @Transactional
    public void create(Plant plant){
        Objects.requireNonNull(plant);
        plantDao.persist(plant);
    }

    @Transactional
    public void addPlantToList(Plant plant, User current_user){
        User user = userDao.find(current_user.getId());
    }
}
