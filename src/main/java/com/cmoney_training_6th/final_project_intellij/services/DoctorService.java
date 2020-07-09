package com.cmoney_training_6th.final_project_intellij.services;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
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
