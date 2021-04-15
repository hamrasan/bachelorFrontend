package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.GardenDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dto.GardenDto;
import cz.fel.cvut.hamrasan.gardener.model.Garden;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GardenService {

    private GardenDao gardenDao;
    private UserDao userDao;
    private TranslateService translateService;


    @Autowired
    public GardenService(GardenDao gardenDao, UserDao userDao, TranslateService translateService) {

        this.gardenDao = gardenDao;
        this.userDao = userDao;
        this.translateService = translateService;
    }

    @Transactional
    public List<GardenDto> findAllOfUser(){
        List<GardenDto> gardenDtos = new ArrayList<>();

        for (Garden garden : SecurityUtils.getCurrentUser().getGardens()) {
            gardenDtos.add(translateService.translateGarden(garden));
        }

        return gardenDtos;
    }
}
