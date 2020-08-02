package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.dao.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getDoctorsByHospitalId(int hospitalId) {
        return doctorRepository.findByHospitalId(hospitalId);
    }

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
