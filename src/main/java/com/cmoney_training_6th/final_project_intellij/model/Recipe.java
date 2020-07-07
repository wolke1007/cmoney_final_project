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
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int medical_item_id; //FK
    private int medical_treatment_id; //FK

//    @Column(nullable=false)
    private int quantity;

}