package cz.fel.cvut.hamrasan.gardener.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CategoryDto {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private List<PlantDto> plants;


    public CategoryDto(@NotNull(message = "Id cannot be blank") Long id, @NotBlank(message = "Name cannot be blank") String name,
                       List<PlantDto> plants) {

        this.id = id;
        this.name = name;
        this.plants = plants;
    }


    public CategoryDto() {
        plants = new ArrayList<PlantDto>();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<PlantDto> getPlants() {
        return plants;
    }


    public void setPlants(List<PlantDto> plants) {
        this.plants = plants;
    }


    public Long getId() {

        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }
}
