package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP_VALVESCHEDULE")
public class ValveSchedule extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private List<String> days;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer hour;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer minutes;

    @ManyToOne
    @JoinColumn(name = "valve_id", nullable = false)
    private Valve valve;


    public ValveSchedule(List<String> days, Integer hour, Integer minutes, Valve valve) {

        this.days = days;
        this.hour = hour;
        this.minutes = minutes;
        this.valve = valve;
    }


    public ValveSchedule() {

    }


    public List<String> getDays() {

        return days;
    }


    public void setDays(List<String> days) {

        this.days = days;
    }


    public Integer getHour() {

        return hour;
    }


    public void setHour(Integer hour) {

        this.hour = hour;
    }


    public Integer getMinutes() {

        return minutes;
    }


    public void setMinutes(Integer minutes) {

        this.minutes = minutes;
    }


    public Valve getValve() {

        return valve;
    }


    public void setValve(Valve valve) {

        this.valve = valve;
    }
}
