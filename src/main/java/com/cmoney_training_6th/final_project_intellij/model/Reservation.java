package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int doctor_id; //FK

    private int user_id; //FK

    private int schedule_id; //FK

    @JoinColumn(name="date")
    private String date;

}