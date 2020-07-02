package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="medical_record_id")
    private MedicalRecord medicalRecord;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="medical_item_id")
    private MedicalItem medicalItem;

//    @Column(nullable=false)
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public MedicalItem getMedicalItem() {
        return medicalItem;
    }

    public void setMedicalItem(MedicalItem medicalItem) {
        this.medicalItem = medicalItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}