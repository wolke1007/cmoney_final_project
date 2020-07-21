package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_treatment")
public class MedicalTreatment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private int weight = 0;
    private String date = "";

    @Column(name="medical_record_id")
    private int medicalRecordId; //FK

//    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    @Column(columnDefinition="nvarchar(20000)")
    private String description = "";

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="medical_treatment_id", referencedColumnName = "id")
    List<Recipe> recipes = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}