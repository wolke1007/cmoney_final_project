package com.cmoney_training_6th.final_project_intellij.repos;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.MedicalItem;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalItemRepository extends JpaRepository<MedicalItem, Integer> {
    Optional<MedicalItem> findByName(String name);
}
