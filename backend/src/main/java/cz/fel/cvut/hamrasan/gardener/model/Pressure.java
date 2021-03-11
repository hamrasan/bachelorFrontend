package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "APP_PRESSURE")
public class Pressure extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate date;

    @Basic(optional = false)
    @Column(nullable = false)
    private float value;

    @ManyToOne
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;


    public Pressure(LocalDate date, float value, Garden garden) {

        this.date = date ;
        this.value = value;
        this.garden = garden;
    }


    public Pressure() { }


    public LocalDate getDate() {

        return date;
    }


    public void setDate(LocalDate localDate) {

        this.date = localDate;
    }


    public float getValue() {

        return value;
    }


    public void setValue(float value) {

        this.value = value;
    }


    public Garden getGarden() {

        return garden;
    }


    public void setGarden(Garden garden) {

        this.garden = garden;
    }
}
