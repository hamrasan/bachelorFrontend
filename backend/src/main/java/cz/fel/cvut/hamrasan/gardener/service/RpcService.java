package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.GardenDao;
import cz.fel.cvut.hamrasan.gardener.dao.HumidityDao;
import cz.fel.cvut.hamrasan.gardener.dao.PressureDao;
import cz.fel.cvut.hamrasan.gardener.dao.TemperatureDao;
import cz.fel.cvut.hamrasan.gardener.dto.TemperatureDto;
import cz.fel.cvut.hamrasan.gardener.model.Humidity;
import cz.fel.cvut.hamrasan.gardener.model.Pressure;
import cz.fel.cvut.hamrasan.gardener.model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RpcService {

    private PressureDao pressureDao;
    private GardenDao gardenDao;
    private TemperatureDao temperatureDao;
    private HumidityDao humidityDao;
    private TranslateService translateService;


    @Autowired
    public RpcService(PressureDao pressureDao, GardenDao gardenDao, TemperatureDao temperatureDao, HumidityDao humidityDao, TranslateService translateService) {

        this.pressureDao = pressureDao;
        this.gardenDao = gardenDao;
        this.temperatureDao = temperatureDao;
        this.humidityDao = humidityDao;
        this.translateService = translateService;
    }


    @Transactional
    public void savePress(String data, String key){
        pressureDao.persist(new Pressure(LocalDate.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(8))) ));
    }

    @Transactional
    public void saveTemperatue(String data, String key){
        temperatureDao.persist(new Temperature(LocalDateTime.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(4))) ));
    }

    @Transactional
    public void saveHumidity(String data, String key){
        humidityDao.persist(new Humidity(LocalDate.now(), Float.parseFloat(data), gardenDao.find(Long.parseLong(key.substring(8))) ));
    }

    @Transactional
    public TemperatureDto getLatestTemperature(){
        return translateService.translateTemp(temperatureDao.findLatest());
    }
}
