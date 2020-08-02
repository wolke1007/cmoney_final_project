package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.User;

import java.util.List;

public interface UserService {
    public abstract void add(int hospitalId);
    public abstract void edit(int doctorId);
    public abstract void delete(int doctorId);
    public abstract List<User> getAllCrewByHospitalId(int hospitalId);
}
