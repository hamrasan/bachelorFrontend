package cz.fel.cvut.hamrasan.gardener.seeder;

import cz.fel.cvut.hamrasan.gardener.dao.*;
import cz.fel.cvut.hamrasan.gardener.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements
        ApplicationListener<ContextRefreshedEvent> {

    private PlantDao plantDao;
    private UserDao userDao;
    private GardenDao gardenDao;
    private PlantCategoryDao plantCategoryDao;
    private RainDao rainDao;

    @Autowired
    public DbSeeder(PlantDao plantDao, UserDao userDao, PlantCategoryDao plantCategorydao, GardenDao gardenDao, RainDao rainDao) {

        this.plantDao = plantDao;
        this.userDao = userDao;
        this.plantCategoryDao = plantCategorydao;
        this.gardenDao = gardenDao;
        this.rainDao = rainDao;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //TODO - vykona sa hned po spusteni
        System.out.println("Vypis po stupusteni aplikacie.");
        createUsers();
        createPlants();
        createGarden();
        createRain();
    }

    @Transactional
    void createPlants(){
        List<Plant> plants = new ArrayList<Plant>();
        PlantCategory category = new PlantCategory("zelenina", plants );
        plantCategoryDao.persist(category);
        Plant plant = new Plant("Rajčina veľká", "../../assets/paradajka-lycopersicum-rajciak-semena.jpg", 12, 35, LocalDate.now(), "Marec", category, gardenDao.findAll());
        plantDao.persist(plant);

        PlantCategory category2 = new PlantCategory("ovocie", new ArrayList<>() );
        plantCategoryDao.persist(category2);
        Plant plant2 = new Plant("Jahoda celoročná", "../../assets/jahoda.jpg", 12, 40, LocalDate.now(), "Celoročne", category2, gardenDao.findAll());
        plantDao.persist(plant2);
    }

    @Transactional
    void createUsers(){
        User user = new User("Jozef", "Pročko", BCrypt.hashpw("hesloo",BCrypt.gensalt()), "jozef@gmail.com");
        userDao.persist(user);
    }

    @Transactional
    void createGarden(){
        List<User> users= userDao.findAll();
        if (users.size()>0){
            Garden garden = new Garden("Zahrada", users.get(0));
            gardenDao.persist(garden);
        }

    }

    @Transactional
    void createRain(){
        rainDao.persist(new Rain(LocalDateTime.now(), false, gardenDao.find((long) 1)));
    }

}
