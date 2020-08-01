package com.cmoney_training_6th.final_project_intellij.dao;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    Optional<Crew> findByUserIdAndHospitalId(int userId, int hospitalId);
    List<Crew> findByHospitalId(int hospitalId);
    Optional<Crew> findByUserId(int userId);
}
