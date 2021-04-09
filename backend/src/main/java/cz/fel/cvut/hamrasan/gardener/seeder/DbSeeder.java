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
    private SubcategoryDao subcategoryDao;
    private UserPlantDao userPlantDao;

    @Autowired
    public DbSeeder(PlantDao plantDao, UserDao userDao, PlantCategoryDao plantCategorydao, GardenDao gardenDao, RainDao rainDao, SubcategoryDao subcategoryDao, UserPlantDao userPlantDao) {

        this.plantDao = plantDao;
        this.userDao = userDao;
        this.plantCategoryDao = plantCategorydao;
        this.subcategoryDao= subcategoryDao;
        this.gardenDao = gardenDao;
        this.rainDao = rainDao;
        this.userPlantDao = userPlantDao;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //TODO - vykona sa hned po spusteni
        System.out.println("Vypis po stupusteni aplikacie.");
        createUsers();
        createCategories();
        createPlants();
        createGarden();
        createUserPlant();
        createRain();
    }

    @Transactional
    void createPlants(){
        List<Plant> plants = new ArrayList<Plant>();
        List<Subcategory> subcategories = new ArrayList<Subcategory>();

        Subcategory subcategory = new Subcategory("Rajčiny", plantCategoryDao.find((long) 1), plants);
        subcategoryDao.persist(subcategory);

        Subcategory subcategory3 = new Subcategory("None", plantCategoryDao.find((long) 1), plants);
        subcategoryDao.persist(subcategory3);

        Plant plant = new Plant("Rajčina veľká", "paradajka-lycopersicum-rajciak-semena.jpg", 12, 35,  "Marec", subcategory);
        plantDao.persist(plant);

        Subcategory subcategory2 = new Subcategory("None", plantCategoryDao.find((long) 2), plants);
        subcategoryDao.persist(subcategory2);

        Plant plant2 = new Plant("Jahoda celoročná", "jahoda.jpg", 12, 40,  "Celoročne", subcategory2);
        plantDao.persist(plant2);

        Plant plant3 = new Plant("Reďkev siata pravá", "redkvicka.jpg", 12, 35,  "Marec", subcategory3);
        plantDao.persist(plant3);
    }

    @Transactional
    void createUsers(){
        User user = new User("Jozef", "Pročko", BCrypt.hashpw("hesloo",BCrypt.gensalt()), "jozef@gmail.com");
        userDao.persist(user);
    }

    @Transactional
    void createGarden(){
        List<User> users= userDao.findAll();

        List<UserPlant> userPlants = new ArrayList<UserPlant>();

        if (users.size()>0){
            Garden garden = new Garden("Zahrada","Praha",users.get(0),userPlants);
            gardenDao.persist(garden);
        }
    }


    @Transactional
    void createUserPlant(){
        List<Plant> plants = plantDao.findAll();

        UserPlant userPlant = new UserPlant(LocalDate.now(), plants.get(0), gardenDao.findAll().get(0));
        userPlantDao.persist(userPlant);

    }

    @Transactional
    void createCategories(){
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        PlantCategory category = new PlantCategory("zelenina", subcategories );
        plantCategoryDao.persist(category);

        PlantCategory category2 = new PlantCategory("ovocie", new ArrayList<>() );
        plantCategoryDao.persist(category2);
    }

    @Transactional
    void createRain(){
        rainDao.persist(new Rain(LocalDateTime.now(), false, gardenDao.find((long) 1)));
    }

}
