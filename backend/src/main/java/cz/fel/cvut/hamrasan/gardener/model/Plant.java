package cz.fel.cvut.hamrasan.gardener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "APP_PLANT")
@NamedQueries({
        @NamedQuery(name = "Plant.findByName", query = "SELECT p FROM Plant p WHERE p.name = :name AND p.deleted_at is null")
})
public class Plant extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    @Size(max = 30, min = 1, message = "Name is in incorrect format.")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String picture;

    @Basic(optional = false)
    @Column(nullable = false)
    private double minTemperature;

    @Basic(optional = false)
    @Column(nullable = false)
    private double maxTemperature;

    @Basic(optional = false)
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate dateOfPlant;

    @Basic(optional = false)
    @Column(nullable = false)
    private String season;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private PlantCategory category;

//    @ManyToMany
//    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Garden> gardens;


    public Plant(@Size(max = 30, min = 1, message = "Name is in incorrect format.") @NotBlank(message = "Name cannot be blank") String name, String picture, double minTemperature,
                 double maxTemperature, @PastOrPresent LocalDate dateOfPlant, String season, PlantCategory category, List<Garden> gardens) {

        this.name = name;
        this.picture = picture;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.dateOfPlant = dateOfPlant;
        this.season = season;
        this.category = category;
//        this.users = users;
        this.gardens = gardens;
    }


    public Plant() {
//        users = new ArrayList<User>();
        gardens = new ArrayList<Garden>();
    }


    @Override
    public String toString() {

        return "Plant{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", dateOfPlant=" + dateOfPlant +
                ", season='" + season + '\'' +
                ", category=" + category +
//                ", users=" + users +
                ", gardens=" + gardens +
                '}';
    }


    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


    public String getPicture() {

        return picture;
    }


    public void setPicture(String picture) {

        this.picture = picture;
    }


    public double getMinTemperature() {

        return minTemperature;
    }


    public void setMinTemperature(double minTemperature) {

        this.minTemperature = minTemperature;
    }


    public double getMaxTemperature() {

        return maxTemperature;
    }


    public void setMaxTemperature(double maxTemperature) {

        this.maxTemperature = maxTemperature;
    }


    public LocalDate getDateOfPlant() {

        return dateOfPlant;
    }


    public void setDateOfPlant(LocalDate dateOfPlant) {

        this.dateOfPlant = dateOfPlant;
    }


    public String getSeason() {

        return season;
    }


    public void setSeason(String season) {

        this.season = season;
    }


    public PlantCategory getCategory() {

        return category;
    }


    public void setCategory(PlantCategory category) {

        this.category = category;
    }


//    public List<User> getUsers() {
//
//        return users;
//    }
//
//
//    public void setUsers(List<User> users) {
//
//        this.users = users;
//    }


    public List<Garden> getGardens() {

        return gardens;
    }


    public void setGardens(List<Garden> gardens) {

        this.gardens = gardens;
    }
}
