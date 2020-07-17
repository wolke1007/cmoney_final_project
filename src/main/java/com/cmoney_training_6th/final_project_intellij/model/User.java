package com.cmoney_training_6th.final_project_intellij.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="social_license_id", unique = true)
    private String socialLicenseId;

    @Column(name="join_time", length=50)
    private String joinTime = "";

    @Column(name="first_name", columnDefinition="nvarchar(50)")
    private String firstName = "";

    @Column(name="last_name", columnDefinition="nvarchar(50)")
    private String lastName = "";

    @Column(nullable=false)
    private String password = "";

    @Column(columnDefinition="nvarchar(255)")
    private String school = "";

    @Column(name="address_city", columnDefinition="nvarchar(255)")
    private String addressCity = "";

    @Column(name="address_area", columnDefinition="nvarchar(255)")
    private String addressArea = "";

    @Column(name="address_line", columnDefinition="nvarchar(255)")
    private String addressLine = "";

//    @Column(nullable=false)
    private String phone = "";

    @Column(length=50)
    private String birthday = "";

    @Column(nullable=false, unique=true)
    private String username;

    @Column(length=50)
    private String role = "ROLE_USER";

    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    List<Pet> pets = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    List<Doctor> doctors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    List<Reservation> reservations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    List<UserPhoto> userPhotos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//
//    public int getHospitalId() {
//        return hospitalId;
//    }
//
//    public void setHospitalId(int hospitalId) {
//        this.hospitalId = hospitalId;
//    }

    public String getSocialLicenseId() {
        return socialLicenseId;
    }

    public void setSocialLicenseId(String socialLicenseId) {
        this.socialLicenseId = socialLicenseId;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName(){
        return this.lastName.toString() + this.firstName.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<UserPhoto> getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(List<UserPhoto> userPhotos) {
        this.userPhotos = userPhotos;
    }
}