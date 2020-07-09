package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="hospital_id")
    private int hospitalId; // FK

//    @Column(nullable=false, length=50)
    @Column(name="create_date", length=50)
    private String createDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="medical_record_id", referencedColumnName = "id")
    List<MedicalTreatment> medicalTreatments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHospital_id() {
        return hospitalId;
    }

    public void setHospital_id(int hospital_id) {
        this.hospitalId = hospital_id;
    }

    public String getCreate_date() {
        return createDate;
    }

    public void setCreate_date(String create_date) {
        this.createDate = create_date;
    }

    public List<MedicalTreatment> getMedicalTreatments() {
        return medicalTreatments;
    }

    public void setMedicalTreatments(List<MedicalTreatment> medicalTreatments) {
        this.medicalTreatments = medicalTreatments;
    }
}