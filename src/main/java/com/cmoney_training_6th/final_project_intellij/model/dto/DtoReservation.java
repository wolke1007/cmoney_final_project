package com.cmoney_training_6th.final_project_intellij.model.dto;

import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.model.User;

public class DtoReservation {
    public int hospitalId;
    public int userId;
    public int petId;
    public int roasterId;
    public String date;

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoasterId() {
        return roasterId;
    }

    public void setRoasterId(int roasterId) {
        this.roasterId = roasterId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
