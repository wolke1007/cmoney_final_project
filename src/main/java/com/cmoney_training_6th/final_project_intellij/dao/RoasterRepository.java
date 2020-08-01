package com.cmoney_training_6th.final_project_intellij.dao;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Roaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoasterRepository extends JpaRepository<Roaster, Integer> {
    List<Roaster> findByDoctorId(int doctorId);

    Optional<Roaster> findByDoctorIdAndScheduleId(int doctorId, int scheduleId);

    @Query(value = "SELECT * FROM newdatabase.roaster WHERE doctor_id = any(" +
            "SELECT id FROM newdatabase.doctor WHERE hospital_id = ?1)",
            nativeQuery = true)
    List<Roaster> findByHospitalId(int hospitalId);

}
