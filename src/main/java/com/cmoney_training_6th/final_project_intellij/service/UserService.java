package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.User;
import com.google.gson.JsonObject;

import java.util.List;

public interface UserService {
    void add(int hospitalId);
    void edit(int doctorId);
    void delete(int doctorId);
    JsonObject getAllCrewByHospitalId(int hospitalId);
    boolean isExist(String jwt);
}
