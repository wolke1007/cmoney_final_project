package com.cmoney_training_6th.final_project_intellij.dao;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByHospitalId(int hospitalId);
    Optional<Doctor> findByUserId(int userId);
    Optional<Doctor> findByUserIdAndHospitalId(int userId, int hospitalId);
}
