package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.dao.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findByHospitalId(int hospitalId){
        return doctorRepository.findByHospitalId(hospitalId);
    }

}
