package com.cmoney_training_6th.final_project_intellij.dao;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.MedicalRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecipeRepository extends JpaRepository<MedicalRecipe, Integer> {
    List<MedicalRecipe> findByMedicalTreatmentId(int medicalTreatmentId);
    List<MedicalRecipe> findByMedicalItemId(int medicalItemId);
}
