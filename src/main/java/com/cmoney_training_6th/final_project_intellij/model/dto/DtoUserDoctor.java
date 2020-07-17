package com.cmoney_training_6th.final_project_intellij.model.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DtoUserDoctor {

    // user properties
    private String socialLicenseId;
    private String joinTime = "";
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private String school = "";
    private String addressCity = "";
    private String addressArea = "";
    private String addressLine = "";
    private String phone = "";
    private String birthday = "";
    private String username;
    // doctor properties
    private String doctorLicense;
    private String experience;
    private String skill;
    private String userName;
    private int hospitalId;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorLicense() {
        return doctorLicense;
    }

    public void setDoctorLicense(String doctorLicense) {
        this.doctorLicense = doctorLicense;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
}
