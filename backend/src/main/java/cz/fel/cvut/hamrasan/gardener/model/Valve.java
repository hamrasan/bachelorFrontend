package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_VALVE")
public class Valve extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    private String picture;

    @OneToMany
    private List<ValveSchedule> valveScheduleList;

    @OneToMany
    private List<Garden> gardens;


    public Valve(String name, String picture, List<ValveSchedule> valveScheduleList, List<Garden> gardens) {

        this.name = name;
        this.picture = picture;
        this.valveScheduleList = valveScheduleList;
        this.gardens = gardens;
    }


    public Valve(String name, List<Garden> gardens) {

        this.name = name;
        this.gardens = gardens;
    }


    public Valve() {
        this.gardens = new ArrayList<Garden>();
        this.valveScheduleList = new ArrayList<ValveSchedule>();
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


    public List<ValveSchedule> getValveScheduleList() {

        return valveScheduleList;
    }


    public void setValveScheduleList(List<ValveSchedule> valveScheduleList) {

        this.valveScheduleList = valveScheduleList;
    }


    public List<Garden> getGardens() {

        return gardens;
    }


    public void setGardens(List<Garden> gardens) {

        this.gardens = gardens;
    }
}
