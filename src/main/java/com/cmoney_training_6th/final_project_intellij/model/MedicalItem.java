package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_item")
public class MedicalItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="item_type", nullable=false, columnDefinition="nvarchar(255)")
    private String itemType = "";

    @Column(nullable=false, unique = true, columnDefinition="nvarchar(255)")
    private String name;

    @Column(columnDefinition="nvarchar(20000)")
    private String description = "";

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="medical_item_id", referencedColumnName = "id")
    List<MedicalRecipe> medicalRecipes = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MedicalRecipe> getRecipes() {
        return medicalRecipes;
    }

    public void setRecipes(List<MedicalRecipe> medicalRecipes) {
        this.medicalRecipes = medicalRecipes;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}