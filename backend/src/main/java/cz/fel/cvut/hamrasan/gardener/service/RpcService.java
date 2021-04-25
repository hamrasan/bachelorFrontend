package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.amqp.rpc.Tut6Client;
import cz.fel.cvut.hamrasan.gardener.dao.*;
import cz.fel.cvut.hamrasan.gardener.dto.*;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Autowired
    public RpcService(PressureDao pressureDao, GardenDao gardenDao, TemperatureDao temperatureDao, HumidityDao humidityDao,
                      TranslateService translateService, Tut6Client client, RainDao rainDao, NotificationService notificationService, SoilDao soilDao) {

        this.pressureDao = pressureDao;
        this.gardenDao = gardenDao;
        this.temperatureDao = temperatureDao;
        this.humidityDao = humidityDao;
        this.translateService = translateService;
        this.client = client;
        this.rainDao = rainDao;
        this.notificationService = notificationService;
        this.soilDao = soilDao;
    }

    @Transactional
    public void savePress(String data, String key){
        pressureDao.persist(new Pressure(LocalDateTime.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(8))) ));
    }

    @Transactional
    public void saveTemperatue(String data, String key) throws NotFoundException {
        Garden garden =  gardenDao.find(Long.parseLong(key.substring(4)));
        temperatureDao.persist(new Temperature(LocalDateTime.now(), Float.parseFloat(data), garden));

        if(Float.parseFloat(data) > 0 ) notificationService.addNotification(garden.getUser().getId(), "Teplota klesla pod 0");

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
    public TemperatureDto getLatestTemperature(){
        return translateService.translateTemp(temperatureDao.findLatest());
    }

    @Transactional
    public HumidityDto getLatestHumidity(){
        return translateService.translateHumidity(humidityDao.findLatest());
    }

    @Transactional
    public PressureDto getLatestPressure(){
        return translateService.translatePressure(pressureDao.findLatest());
    }

    @Transactional
    public RainDto getLatestRain(){
        return translateService.translateRain(rainDao.findLatest());
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
    public SoilDto getLatestSoil() {
        return translateService.translateSoil(soilDao.findLatest());
    }
}
