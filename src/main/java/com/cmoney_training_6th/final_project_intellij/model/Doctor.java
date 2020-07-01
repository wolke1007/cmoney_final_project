package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="hospital_id")
    private Hospital hospital;

    private String doctor_license;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getDoctor_license() {
        return doctor_license;
    }

    public void setDoctor_license(String doctor_license) {
        this.doctor_license = doctor_license;
    }
}