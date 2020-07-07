package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_treatment")
public class MedicalTreatment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="medical_record_id")
    private int medicalRecordId; //FK

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Recipe> recipes = new ArrayList<>();

//    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    @Column(columnDefinition="nvarchar(20000)")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedical_record_id() {
        return medicalRecordId;
    }

    public void setMedical_record_id(int medical_record_id) {
        this.medicalRecordId = medical_record_id;
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
}