package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    // FK
    private int hospital_id;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Doctor> doctors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Reservation> reservations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<UserPhoto> userPhotos = new ArrayList<>();
}