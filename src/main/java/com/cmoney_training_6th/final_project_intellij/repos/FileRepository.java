package com.cmoney_training_6th.final_project_intellij.repos;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<PetPhoto, Integer> {
}
