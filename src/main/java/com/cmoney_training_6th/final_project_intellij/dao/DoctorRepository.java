package com.cmoney_training_6th.final_project_intellij.dao;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByHospitalId(int hospitalId);
    Optional<Doctor> findByUserId(int userId);
    Optional<Doctor> findByUserIdAndHospitalId(int userId, int hospitalId);
}
