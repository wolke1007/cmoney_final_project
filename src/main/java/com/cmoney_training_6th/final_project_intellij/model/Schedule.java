package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int schedule_id;

    @Column(nullable=false, length=50)
    private String day;

    @Column(nullable=false, length=50)
    private String time;

}