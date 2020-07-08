package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(length=50)
    private String day;

    private String time;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="schedule_id", referencedColumnName = "id")
    List<Roaster> roasters = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Roaster> getRoasters() {
        return roasters;
    }

    public void setRoasters(List<Roaster> roasters) {
        this.roasters = roasters;
    }
}