package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.amqp.rpc.Tut6Client;
import cz.fel.cvut.hamrasan.gardener.dao.*;
import cz.fel.cvut.hamrasan.gardener.dto.*;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.model.*;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RpcService {

    private PressureDao pressureDao;
    private GardenDao gardenDao;
    private TemperatureDao temperatureDao;
    private HumidityDao humidityDao;
    private TranslateService translateService;
    private Tut6Client client;
    private RainDao rainDao;
    private NotificationService notificationService;
    private SoilDao soilDao;
    private UserDao userDao;

    @Autowired
    public RpcService(PressureDao pressureDao, GardenDao gardenDao, TemperatureDao temperatureDao, HumidityDao humidityDao,
                      TranslateService translateService, Tut6Client client, RainDao rainDao, NotificationService notificationService, SoilDao soilDao,
                      UserDao userDao) {

        this.pressureDao = pressureDao;
        this.gardenDao = gardenDao;
        this.temperatureDao = temperatureDao;
        this.humidityDao = humidityDao;
        this.translateService = translateService;
        this.client = client;
        this.rainDao = rainDao;
        this.notificationService = notificationService;
        this.soilDao = soilDao;
        this.userDao = userDao;
    }

    @Transactional
    public void savePress(String data, String key){
        pressureDao.persist(new Pressure(LocalDateTime.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(8))) ));
    }

    @Transactional
    public void saveTemperatue(String data, String key) throws NotFoundException {
        Garden garden =  gardenDao.find(Long.parseLong(key.substring(4)));
        temperatureDao.persist(new Temperature(LocalDateTime.now(), Float.parseFloat(data), garden));

//        if(Float.parseFloat(data) > 0 ) notificationService.addNotification(garden.getUser().getId(), "Teplota klesla pod 0");

    }

    @Transactional
    public void saveHumidity(String data, String key){
        humidityDao.persist(new Humidity(LocalDateTime.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(8))) ));
    }

    @Transactional
    public void saveRain(String data, String key){
        boolean raining = false;
        if (data.equals("1")) raining = true;
        rainDao.persist(new Rain(LocalDateTime.now(), raining, gardenDao.find(Long.parseLong(key.substring(4))) ));
    }

    @Transactional
    public TemperatureDto getLatestTemperature(Long gardenId) throws NotAllowedException {
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId())  throw new NotAllowedException("Not your garden");

        return translateService.translateTemp(temperatureDao.findLatest(garden ));
    }

    @Transactional
    public HumidityDto getLatestHumidity(Long gardenId) throws NotAllowedException {
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId())  throw new NotAllowedException("Not your garden");

        return translateService.translateHumidity(humidityDao.findLatest(garden));
    }

    @Transactional
    public PressureDto getLatestPressure(Long gardenId) throws NotAllowedException {
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId())  throw new NotAllowedException("Not your garden");

        return translateService.translatePressure(pressureDao.findLatest(garden));
    }

    @Transactional
    public RainDto getLatestRain(Long gardenId) throws NotAllowedException {
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId())  throw new NotAllowedException("Not your garden");

        return translateService.translateRain(rainDao.findLatest(garden));
    }

    @Transactional
    public void setMeassureMinutes(String minutes){
        client.setMeassureMinutes(minutes);
    }

    @Transactional
    public void saveSoil(String data, String key) throws NotFoundException {
        soilDao.persist(new Soil(LocalDateTime.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(4))) ));
    }

    @Transactional
    public SoilDto getLatestSoil(Long gardenId) throws NotAllowedException {
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        return translateService.translateSoil(soilDao.findLatest(garden));
    }

    @Transactional
    public List<SoilDto> getHistorySoil(Long gardenId) throws NotAllowedException {
        List<SoilDto> soilDtos = new ArrayList<>();
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        for (Soil soil: soilDao.findHistoryOfGarden(garden) ) {
            soilDtos.add(translateService.translateSoil(soil));
        }

        return soilDtos;
    }

    @Transactional
    public List<RainDto> getHistoryRain(Long gardenId) throws NotAllowedException {
        List<RainDto> rainDtos = new ArrayList<>();
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        for (Rain rain: rainDao.findHistoryOfGarden(garden) ) {
            rainDtos.add(translateService.translateRain(rain));
        }

        return rainDtos;
    }

    @Transactional
    public List<HumidityDto> getHistoryHumidity(Long gardenId) throws NotAllowedException {
        List<HumidityDto> humidityDtos = new ArrayList<>();
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        for (Humidity humidity: humidityDao.findHistoryOfGarden(garden) ) {
            humidityDtos.add(translateService.translateHumidity(humidity));
        }
        return humidityDtos;
    }

    @Transactional
    public List<PressureDto> getHistoryPressure(Long gardenId) throws NotAllowedException {
        List<PressureDto> pressureDtos = new ArrayList<>();
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        for (Pressure pressure: pressureDao.findHistoryOfGarden(garden) ) {
            pressureDtos.add(translateService.translatePressure(pressure));
        }

        return pressureDtos;
    }

    @Transactional
    public List<TemperatureDto> getHistoryTemperature(Long gardenId) throws NotAllowedException {
        List<TemperatureDto> temperatureDtos = new ArrayList<>();
        Garden garden = gardenDao.find(gardenId);
        User user = SecurityUtils.getCurrentUser();
        if(garden.getUser().getId() != user.getId()) throw new NotAllowedException("Not your garden");

        for (Temperature temperature: temperatureDao.findHistoryOfGarden(garden) ) {
            temperatureDtos.add(translateService.translateTemp(temperature));
        }

        return temperatureDtos;
    }
}
