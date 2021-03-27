package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dto.*;
import cz.fel.cvut.hamrasan.gardener.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TranslateService {

    @Transactional
    public UserDto translateUser(User user) {
        System.out.println(user.toString());
        Objects.requireNonNull(user);

        List<GardenDto> gardenDtos = new ArrayList<GardenDto>();
        List<Garden> gardens = user.getGardens();


        if (gardens.size() > 0){
            gardens.forEach(garden-> gardenDtos.add(translateGarden(garden)));
        }

        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(), gardenDtos);
    }

    @Transactional
    public PlantDto translateUserPlant(UserPlant plant){
        Objects.requireNonNull(plant);
        List<Long> gardenDtos = new ArrayList<Long>();
        Plant plant1 = plant.getPlant();
        List<Garden> gardens = plant.getGardens();

        if(gardens.size() > 0){
            gardens.forEach(garden -> gardenDtos.add(garden.getId()));
        }

        return new PlantDto(plant.getId(), plant1.getName(), plant1.getPicture(), plant1.getMinTemperature(),
                plant1.getMaxTemperature(), plant.getDateOfPlant(), plant1.getSeason(), translatePlantCategory(plant1.getCategory()), gardenDtos );
    }


    @Transactional
    public PlantWithoutDateDto translatePlant(Plant plant){
        Objects.requireNonNull(plant);

        return new PlantWithoutDateDto(plant.getId(), plant.getName(), plant.getPicture(), plant.getMinTemperature(),
                plant.getMaxTemperature(), plant.getSeason(), translatePlantCategory(plant.getCategory()) );
    }

    @Transactional
    public GardenDto translateGarden(Garden garden){
        Objects.requireNonNull(garden);
        List<TemperatureDto> temperatureDtos = new ArrayList<>();
        List<PressureDto> pressureDtos = new ArrayList<>();
        List<HumidityDto> humidityDtos = new ArrayList<>();
        List<PlantDto> plantDtos = new ArrayList<>();

        List<Temperature> temperatures = garden.getTemperatures();
        List<Humidity> humidities = garden.getHumidities();
        List<Pressure> pressures = garden.getPressures();
        List<UserPlant> plants = garden.getPlants();

        if (temperatures.size() > 0){
            temperatures.forEach(temperature -> temperatureDtos.add(translateTemp(temperature)));
        }

        if (humidities.size() > 0){
            humidities.forEach(humidity -> humidityDtos.add(translateHumidity(humidity)));
        }

        if (pressures.size() > 0){
            pressures.forEach(pressure -> pressureDtos.add(translatePressure(pressure)));
        }

        if (plants.size() > 0){
            plants.forEach(plant -> plantDtos.add(translateUserPlant(plant)));
        }

        return new GardenDto(garden.getId(), garden.getName(), garden.getLocation(), temperatureDtos, humidityDtos, pressureDtos,
                garden.getUser().getId(), plantDtos);
    }

    @Transactional
    public PlantCategoryDto translatePlantCategory(PlantCategory plantCategory){
        Objects.requireNonNull(plantCategory);
        return new PlantCategoryDto(plantCategory.getId(), plantCategory.getName());
    }

    @Transactional
    public TemperatureDto translateTemp(Temperature temperature){
        return new TemperatureDto(temperature.getId(),temperature.getDate(), temperature.getValue(), temperature.getGarden().getId());
    }

    @Transactional
    public PressureDto translatePressure(Pressure pressure){
        return new PressureDto(pressure.getId(),pressure.getDate(), pressure.getValue(), pressure.getGarden().getId());
    }

    @Transactional
    public HumidityDto translateHumidity(Humidity humidity){
        return new HumidityDto(humidity.getId(),humidity.getDate(), humidity.getValue(), humidity.getGarden().getId());
    }

    @Transactional
    public CategoryDto translateCategory(PlantCategory plantCategory){
        Objects.requireNonNull(plantCategory);
        List<PlantWithoutDateDto> plantDtos = new ArrayList<PlantWithoutDateDto>();
        List<Plant> plants = plantCategory.getPlants();

        if(plants.size() > 0){
            for (Plant plant: plants) {
                plantDtos.add(translatePlant(plant));
            }
        }
        return new CategoryDto(plantCategory.getId(), plantCategory.getName(),plantDtos );
    }

    @Transactional
    public RainDto translateRain(Rain rain){
        Objects.requireNonNull(rain);
        return new RainDto(rain.getId(), rain.getDate(), rain.getRaining(), rain.getGarden().getId());
    }
}
