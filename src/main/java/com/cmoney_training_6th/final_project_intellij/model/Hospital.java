package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="user_id")
    private int userId; // FK

//    @Column(nullable=false, unique=true)
    @Column(name="uni_serial_id", unique=true)
    private int uniSerialId;

//    @Column(nullable=false, columnDefinition="nvarchar(255)")
    @Column(columnDefinition="nvarchar(255)")
    private String name;

    private String phone;

    @Column(name="address_city", columnDefinition="nvarchar(255)")
    private String addressCity;

    @Column(name="address_area", columnDefinition="nvarchar(255)")
    private String addressArea;

    @Column(name="address_line", columnDefinition="nvarchar(255)")
    private String addressLine;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="hospital_id", referencedColumnName = "id")
    List<Announcement> announcements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="hospital_id", referencedColumnName = "id")
    List<Doctor> doctors = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_id", referencedColumnName = "id")
//    User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="hospital_id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniSerialId() {
        return uniSerialId;
    }

    public void setUniSerialId(int uniSerialId) {
        this.uniSerialId = uniSerialId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}