package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_VALVESCHEDULE")
@NamedQueries({
        @NamedQuery(name = "ValveSchedule.findByValve", query = "SELECT v FROM ValveSchedule v WHERE v.valve.id = :valveId AND v.deleted_at is null")
})
public class ValveSchedule extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Max(24)
    @Min(0)
    private Integer hour;

    @Basic(optional = false)
    @Column(nullable = false)
    @Max(60)
    @Min(0)
    private Integer minutes;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(0)
    @Max(1380)
    private Integer length;

    @ManyToOne
    @JoinColumn(name = "valve_id", nullable = false)
    private Valve valve;

    @Basic(optional = false)
    @ElementCollection
    @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "day_num", length = 1)
    private List<Integer> days;


    public ValveSchedule(@Max(24) @Min(0) Integer hour, @Max(60) @Min(0) Integer minutes, @Min(0) @Max(1380) Integer length, Valve valve, List<Integer> days) {

        this.hour = hour;
        this.minutes = minutes;
        this.length = length;
        this.valve = valve;
        this.days = days;
    }


    public ValveSchedule() {
        days = new ArrayList<Integer>();
    }


    public List<Integer> getDays() {

        return days;
    }


    public void setDays(List<Integer> days) {

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


    public Integer getLength() {

        return length;
    }


    public void setLength(Integer length) {

        this.length = length;
    }
}
