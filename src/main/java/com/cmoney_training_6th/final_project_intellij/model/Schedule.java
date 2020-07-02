package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

//    @Column(nullable=false, length=50)
    @Column(length=50)
    private String day;


    private String time;

}