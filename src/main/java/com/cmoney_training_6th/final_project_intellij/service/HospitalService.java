package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;

import java.util.List;

public interface HospitalService {
    public abstract CommonResponse add(Hospital newHospital);
    public abstract CommonResponse edit(int hospitalId);
    public abstract CommonResponse delete(int hospitalId);
}
