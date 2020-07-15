package com.cmoney_training_6th.final_project_intellij.model.dto;

import com.cmoney_training_6th.final_project_intellij.model.Crew;
import com.cmoney_training_6th.final_project_intellij.model.User;

public class DtoCrewUser {
    private User user;
    private int hospitalId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
}
