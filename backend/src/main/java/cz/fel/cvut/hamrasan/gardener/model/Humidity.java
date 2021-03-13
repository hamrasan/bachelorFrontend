package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "APP_HUMIDITY")
@NamedQueries({
        @NamedQuery(name = "Humidity.findLatest", query = "SELECT h FROM Humidity h WHERE h.deleted_at is null ORDER BY h.date DESC ")
})
public class Humidity extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate date;

    @Basic(optional = false)
    @Column(nullable = false)
    private float value;

    @ManyToOne
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;


    public Humidity(LocalDate date, float value, Garden garden) {

        this.date = date;
        this.value = value;
        this.garden = garden;
    }


    public Humidity() {

    }


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
