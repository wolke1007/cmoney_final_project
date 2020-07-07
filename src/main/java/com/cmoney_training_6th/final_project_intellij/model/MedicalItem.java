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
@Table(name = "medical_item")
public class MedicalItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    List<Recipe> recipes = new ArrayList<>();

    @Column(nullable=false)
    private String item_type;

    @Column(nullable=false, columnDefinition="nvarchar(255)")
    private String name;

    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    private String description;

}