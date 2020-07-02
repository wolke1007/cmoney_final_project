package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_item")
public class MedicalItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private String item_type;

    @Column(nullable=false, columnDefinition="nvarchar(255)")
    private String name;

    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
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