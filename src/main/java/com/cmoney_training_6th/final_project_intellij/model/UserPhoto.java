package com.cmoney_training_6th.final_project_intellij.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user_photo")
public class UserPhoto {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private int user_id; //FK

    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}