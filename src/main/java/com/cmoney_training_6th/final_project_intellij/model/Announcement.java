package com.cmoney_training_6th.final_project_intellij.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //    @Column(nullable=false, columnDefinition="nvarchar(255)")
    @Column(columnDefinition = "nvarchar(255)")
    private String title;

    //    @Column(nullable=false, columnDefinition="nvarchar(20000)")
    @Column(columnDefinition = "nvarchar(20000)")
    private String description;

    private int hospital_id;

}