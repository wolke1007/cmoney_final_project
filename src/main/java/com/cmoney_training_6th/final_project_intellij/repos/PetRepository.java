package com.cmoney_training_6th.final_project_intellij.repos;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.Schedule;
import com.cmoney_training_6th.final_project_intellij.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserId(int userId);
    Optional<Pet> findByUserIdAndPetId(int userId, int petId);
}
