package cz.fel.cvut.hamrasan.gardener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_USER_PLANT")
@NamedQueries({
        @NamedQuery(name = "UserPlant.findByName", query = "SELECT p FROM UserPlant p WHERE p.plant.name = :name AND p.deleted_at is null")
})
public class UserPlant extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate dateOfPlant;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;


    public UserPlant(@PastOrPresent LocalDate dateOfPlant, Plant plant, Garden garden) {

        this.dateOfPlant = dateOfPlant;
        this.plant = plant;
        this.garden = garden;
    }


    public UserPlant(){
    }


    public LocalDate getDateOfPlant() {

        return dateOfPlant;
    }


    public void setDateOfPlant(LocalDate dateOfPlant) {

        this.dateOfPlant = dateOfPlant;
    }


    public Plant getPlant() {

        return plant;
    }


    public void setPlant(Plant plant) {

        this.plant = plant;
    }


    public Garden getGarden() {

        return garden;
    }


    public void setGarden(Garden garden) {

        this.garden = garden;
    }


    @Override
    public String toString() {

        return "UserPlant{" +
                "dateOfPlant=" + dateOfPlant +
                ", plant=" + plant +
                ", garden=" + garden +
                '}';
    }
}
