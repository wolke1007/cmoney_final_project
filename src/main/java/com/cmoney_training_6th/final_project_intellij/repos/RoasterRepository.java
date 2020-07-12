package com.cmoney_training_6th.final_project_intellij.repos;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Reservation;
import com.cmoney_training_6th.final_project_intellij.model.Roaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoasterRepository extends JpaRepository<Roaster, Integer> {
    List<Roaster> findByDoctorId(int doctorId);

    List<Roaster> findByScheduleId(int doctorId);

    @Query(value = "SELECT * FROM newdatabase.roaster WHERE doctor_id = any(" +
            "SELECT id FROM newdatabase.doctor WHERE hospital_id = ?1)",
            nativeQuery = true)
    List<Roaster> findByHospitalId(int hospitalId);
}
