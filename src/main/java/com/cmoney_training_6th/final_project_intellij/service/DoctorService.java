package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;

import java.util.List;

public interface DoctorService {
    public abstract List<Doctor> getDoctorsByHospitalId(int hospitalId);
    public abstract void add(int hospitalId);
    public abstract void edit(int doctorId);
    public abstract void delete(int doctorId);
}
