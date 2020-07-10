package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name="id")
    private int id;

    @Column(name="hospital_id")
    private int hospitalId; // FK

    @Column(name="user_id")
    private int userId; // FK

    @Column(name="pet_id")
    private int petId; // FK

    @Column(name="create_date", length=50)
    private String createDate;

//    @OneToOne
//    @JoinColumn(name = "pet_id", unique = true)
//    protected Pet pet;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="medical_record_id", referencedColumnName = "id")
    List<MedicalTreatment> medicalTreatments = new ArrayList<>();

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

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

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<MedicalTreatment> getMedicalTreatments() {
        return medicalTreatments;
    }

    public void setMedicalTreatments(List<MedicalTreatment> medicalTreatments) {
        this.medicalTreatments = medicalTreatments;
    }
}