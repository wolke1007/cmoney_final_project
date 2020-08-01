package com.cmoney_training_6th.final_project_intellij.dao;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    List<MedicalRecord> findByUserId(int userId);
    Optional<MedicalRecord> findByUserIdAndPetId(int userId, int petId);
    Optional<MedicalRecord> findByUserIdAndPetIdAndHospitalId(int userId, int petId, int hospitalId);
}
