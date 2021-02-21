package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.PlantDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlantService {

    private PlantDao plantDao;
    private UserDao userDao;


    @Autowired
    public PlantService(PlantDao plantDao, UserDao userDao) {
        this.plantDao = plantDao;
        this.userDao = userDao;
    }

    public List<Plant> findAll() {
        return plantDao.findAll();
    }


    public Plant find(Long id) {
        return plantDao.find(id);
    }


    public Plant find(String name) {
        return plantDao.findByName(name);
    }

    @Transactional
    public List<Plant> getUserPlants(){

      return userDao.find(SecurityUtils.getCurrentUser().getId()).getPlants();
    }
}
