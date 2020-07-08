package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="user_id")
    private int userId; //FK

    @Column(name="roaster_id")
    private int roasterId; //FK

    @Column(length=50)
    private String date;

    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoasterId() {
        return roasterId;
    }

    public void setRoasterId(int roasterId) {
        this.roasterId = roasterId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}