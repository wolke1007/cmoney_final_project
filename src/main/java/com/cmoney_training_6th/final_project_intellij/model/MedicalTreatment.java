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
@Table(name = "medical_treatment")
public class MedicalTreatment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int medical_record_id; //FK

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Recipe> recipes = new ArrayList<>();

//    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    @Column(columnDefinition="nvarchar(20000)")
    private String description;

}