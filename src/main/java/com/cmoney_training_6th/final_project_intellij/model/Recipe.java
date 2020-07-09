package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="medical_item_id")
    private int medicalItemId; //FK

    @Column(name="medical_treatment_id")
    private int medicalTreatmentId; //FK

//    @Column(nullable=false)
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalItemId() {
        return medicalItemId;
    }

    public void setMedicalItemId(int medicalItemId) {
        this.medicalItemId = medicalItemId;
    }

    public int getMedicalTreatmentId() {
        return medicalTreatmentId;
    }

    public void setMedicalTreatmentId(int medicalTreatmentId) {
        this.medicalTreatmentId = medicalTreatmentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}