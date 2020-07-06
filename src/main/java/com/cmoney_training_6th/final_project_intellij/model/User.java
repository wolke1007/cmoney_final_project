package com.cmoney_training_6th.final_project_intellij.model;

import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

//    @Column(nullable=false, unique=true)
    private String social_license_id;

    @Column(length=50)
    private String join_time;

//    @Column(nullable=false, columnDefinition="nvarchar(50)")
    @Column(columnDefinition="nvarchar(50)")
    private String first_name;

//    @Column(nullable=false, columnDefinition="nvarchar(50)")
    @Column(columnDefinition="nvarchar(50)")
    private String last_name;

    @Column(nullable=false)
    private String password;

    @Column(columnDefinition="nvarchar(255)")
    private String school;

    @Column(columnDefinition="nvarchar(255)")
    private String address_city;

    @Column(columnDefinition="nvarchar(255)")
    private String address_area;

    @Column(columnDefinition="nvarchar(255)")
    private String address_line;

//    @Column(nullable=false)
    private String phone;

    @Column(length=50)
    private String birthday;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(length=50)
    private String role = "ROLE_USER";

    private boolean active = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSocial_license_id() {
        return social_license_id;
    }

    public void setSocial_license_id(String social_license_id) {
        this.social_license_id = social_license_id;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_area() {
        return address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}