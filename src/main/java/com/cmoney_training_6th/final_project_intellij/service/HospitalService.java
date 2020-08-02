package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;

import java.util.List;

public interface HospitalService {
    CommonResponse add(Hospital newHospital);
    CommonResponse edit(int hospitalId);
    CommonResponse delete(int hospitalId);
}
