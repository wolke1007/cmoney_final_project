package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.dao.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Optional<Hospital> findById(int hospitalId){
        return hospitalRepository.findById(hospitalId);
    }

}
