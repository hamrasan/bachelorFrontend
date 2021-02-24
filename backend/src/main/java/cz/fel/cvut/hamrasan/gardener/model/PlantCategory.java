package cz.fel.cvut.hamrasan.gardener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_PLANT_CATEGORY")public class PlantCategory extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    @Size(max = 50, min = 1, message = "Name is in incorrect format.")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Plant> plants;


    public PlantCategory(@Size(max = 50, min = 1, message = "Name is in incorrect format.")
                         @NotBlank(message = "Name cannot be blank") String name, List<Plant> plants) {
        this.name = name;
        this.plants = plants;
    }


    public PlantCategory() {
        this.plants = new ArrayList<Plant>();
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Plant> getPlants() {
        return plants;
    }


    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
