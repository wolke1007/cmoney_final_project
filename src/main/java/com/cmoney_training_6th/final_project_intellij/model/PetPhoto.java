package com.cmoney_training_6th.final_project_intellij.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "寵物照片")
@Entity
public class PetPhoto implements Serializable {

    @ApiModelProperty(value = "照片ID",required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "照片名稱",required = true)
    private String name;

    @ApiModelProperty(value = "照片URL",required = true)
    private String url;

    @ApiModelProperty(value = "日期",required = true)
    private String date;

    @ApiModelProperty(value = "時間",required = true)
    private long time;

    @ApiModelProperty(value = "寵物ID",required = true)
    @Column(name="pet_id")
    private int petId;

    public PetPhoto(){}

    public PetPhoto(String filename, String url, int petId){
        this.name = filename;
        this.url = url;
        this.petId = petId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}