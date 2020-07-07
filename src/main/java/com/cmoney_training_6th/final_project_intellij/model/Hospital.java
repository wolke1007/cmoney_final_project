package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

//    @Column(nullable=false, unique=true)
    @Column(unique=true)
    private int uni_serial_id;

//    @Column(nullable=false, columnDefinition="nvarchar(255)")
    @Column(columnDefinition="nvarchar(255)")
    private String name;

    private String phone;

    @Column(columnDefinition="nvarchar(255)")
    private String address_city;

    @Column(columnDefinition="nvarchar(255)")
    private String address_area;

    @Column(columnDefinition="nvarchar(255)")
    private String address_line;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Announcement> announcements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Doctor> doctors = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_user_id", referencedColumnName = "id")
    User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

}