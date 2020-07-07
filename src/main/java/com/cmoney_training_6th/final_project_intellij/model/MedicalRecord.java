package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int hospital_id; // FK

//    @Column(nullable=false, length=50)
    @Column(length=50)
    private String create_date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<MedicalTreatment> medicalTreatments = new ArrayList<>();

}