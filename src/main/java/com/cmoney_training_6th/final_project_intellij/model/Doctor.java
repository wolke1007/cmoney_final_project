package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="hospital_id")
    private int hospitalId; //FK

    @Column(name="user_id")
    private int userId; //FK

    @Column(name="doctor_license", unique = true)
    private String doctorLicense;

    @Column(columnDefinition="nvarchar(255)")
    private String skill = "";

    @Column(columnDefinition="nvarchar(1000)")
    private String experience = "";

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id", referencedColumnName = "id")
    List<Roaster> roasters = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDoctorLicense() {
        return doctorLicense;
    }

    public void setDoctorLicense(String doctorLicense) {
        this.doctorLicense = doctorLicense;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<Roaster> getRoasters() {
        return roasters;
    }

    public void setRoasters(List<Roaster> roasters) {
        this.roasters = roasters;
    }
}