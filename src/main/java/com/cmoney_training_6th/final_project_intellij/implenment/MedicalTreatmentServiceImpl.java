package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalTreatmentServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void add(int hospitalId){

    }

    @Override
    public void edit(int doctorId) {

    }

    @Override
    public void delete(int doctorId) {

    }
}
