package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.dao.UserRepository;
import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.service.DoctorService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(int hospitalId) {

    }

    @Override
    public void edit(int doctorId) {

    }

    @Override
    public void delete(int doctorId) {

    }

    @Override
    public List<User> getAllCrewByHospitalId(int hospitalId) {
        return null;
    }
}
