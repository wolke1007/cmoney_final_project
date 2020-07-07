package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int hospital_id; //FK

    private int user_id; //FK

    private String doctor_license;

    @Column(columnDefinition="nvarchar(255)")
    private String skill;

    @Column(columnDefinition="nvarchar(1000)")
    private String experience;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Reservation> reservations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Roaster> roasters = new ArrayList<>();
}