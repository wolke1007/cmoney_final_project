package com.cmoney_training_6th.final_project_intellij.model;

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

}