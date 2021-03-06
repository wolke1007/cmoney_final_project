package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @Column(nullable=false, columnDefinition="nvarchar(255)")
    @Column(columnDefinition = "nvarchar(255)")
    private String title = "";

    //    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    @Column(columnDefinition = "nvarchar(20000)")
    private String description = "";

    @Column(name="hospital_id")
    private int hospitalId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHospital_id() {
        return hospitalId;
    }

    public void setHospital_id(int hospital_id) {
        this.hospitalId = hospital_id;
    }
}