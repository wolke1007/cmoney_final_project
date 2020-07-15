package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId; //FK

    @Column(name="hospital_id")
    private int hospitalId; //FK

}