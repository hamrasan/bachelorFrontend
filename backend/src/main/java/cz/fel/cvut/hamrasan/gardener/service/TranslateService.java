package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dto.CategoryDto;
import cz.fel.cvut.hamrasan.gardener.dto.PlantCategoryDto;
import cz.fel.cvut.hamrasan.gardener.dto.PlantDto;
import cz.fel.cvut.hamrasan.gardener.dto.UserDto;
import cz.fel.cvut.hamrasan.gardener.model.Plant;
import cz.fel.cvut.hamrasan.gardener.model.PlantCategory;
import cz.fel.cvut.hamrasan.gardener.model.User;
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

        List<PlantDto> plantsDtos = new ArrayList<PlantDto>();
        List<Plant> plants = user.getPlants();


        if (plants.size() > 0){
            plants.forEach(plant-> plantsDtos.add(translatePlant(plant)));
        }

        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(), plantsDtos);
    }

    @Transactional
    public PlantDto translatePlant(Plant plant){
        Objects.requireNonNull(plant);
        List<Long> usersDtos = new ArrayList<Long>();
        List<User> users = plant.getUsers();

        if(users.size() > 0){
            users.forEach(user -> usersDtos.add(user.getId()));
        }

        return new PlantDto(plant.getId(), plant.getName(), plant.getPicture(), plant.getMinTemperature(),
                plant.getMaxTemperature(), plant.getDateOfPlant(), plant.getSeason(), translatePlantCategory(plant.getCategory()), usersDtos );
    }

    @Transactional
    public PlantCategoryDto translatePlantCategory(PlantCategory plantCategory){
        Objects.requireNonNull(plantCategory);
        return new PlantCategoryDto(plantCategory.getId(), plantCategory.getName());
    }

    @Transactional
    public CategoryDto translateCategory(PlantCategory plantCategory){
        Objects.requireNonNull(plantCategory);
        List<PlantDto> plantDtos = new ArrayList<PlantDto>();
        List<Plant> plants = plantCategory.getPlants();

        if(plants.size() > 0){
            for (Plant plant: plants) {
                plantDtos.add(translatePlant(plant));
            }
        }
        return new CategoryDto(plantCategory.getId(), plantCategory.getName(),plantDtos );
    }
}
