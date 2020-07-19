package com.cmoney_training_6th.final_project_intellij.repos;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import com.cmoney_training_6th.final_project_intellij.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetPhotoRepository extends JpaRepository<PetPhoto, Integer> {
    List<PetPhoto> findAllByPetId(int petId);

    @Query(value = "SELECT pet_photo.* FROM pet_photo\n" +
            "JOIN pet WHERE pet.id = pet_photo.pet_id AND pet.user_id = ?1", nativeQuery = true)
    List<PetPhoto> findPetPhotosByUserId(int userId);

}
