package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "pet_photo")
public class PetPhoto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private int pet_id; //FK

    private String path;

}