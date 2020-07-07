package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="hospital_id")
    private int hospitalId; //FK

    @Column(name="user_id")
    private int userId; //FK

    @Column(name="doctor_license")
    private String doctorLicense;

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

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public String getDoctor_license() {
        return doctorLicense;
    }

    public void setDoctor_license(String doctor_license) {
        this.doctorLicense = doctor_license;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Roaster> getRoasters() {
        return roasters;
    }

    public void setRoasters(List<Roaster> roasters) {
        this.roasters = roasters;
    }
}