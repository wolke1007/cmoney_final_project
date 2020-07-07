package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int user_id; //FK

    @Column(columnDefinition="nvarchar(255)")
    private String name;

    private int age;

    private int weight;

    private int gender;

    @Column(columnDefinition="nvarchar(255)")
    private String breed;

    @Column(columnDefinition="nvarchar(255)")
    private String species;

    private String chip;

    @Column(columnDefinition="nvarchar(255)")
    private String allergic_with;

    private boolean neutered;

    @Column(length=50)
    private String own_date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<PetPhoto> petPhotos = new ArrayList<>();
}