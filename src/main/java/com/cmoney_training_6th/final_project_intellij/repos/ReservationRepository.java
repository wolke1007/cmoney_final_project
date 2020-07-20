package com.cmoney_training_6th.final_project_intellij.repos;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Reservation;
import com.cmoney_training_6th.final_project_intellij.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByRoasterIdAndDate(int roasterId, String date);
    List<Reservation> findAllByRoasterIdAndDateAndUserId(int roasterId, String date, int userId);

    @Query( value = "SELECT * FROM newdatabase.reservation WHERE user_id = ?1 AND roaster_id = any(" +
            "SELECT id FROM newdatabase.roaster WHERE doctor_id = any(" +
            "SELECT id FROM newdatabase.doctor WHERE hospital_id = ?2))",
            nativeQuery = true)
    List<Reservation> findReservationByUserIdAndHospitalId(int userId, int hospitalId);

    @Query( value = "SELECT * FROM newdatabase.reservation WHERE roaster_id = any(" +
            "SELECT id FROM newdatabase.roaster WHERE doctor_id = any(" +
            "SELECT id FROM newdatabase.doctor WHERE hospital_id = ?1))",
            nativeQuery = true)
    List<Reservation> findReservationByHospitalId(int hospitalId);

    Optional<Reservation> findByUserIdAndPetIdAndRoasterIdAndDate(
            int userId, int petId, int roasterId, String date);

    List<Reservation> findReservationByUserId(int userId);

    @Query( value = "SELECT * FROM newdatabase.reservation WHERE roaster_id = any(" +
            "SELECT id FROM newdatabase.roaster WHERE doctor_id = ?1)",
            nativeQuery = true)
    List<Reservation> findReservationByDoctorId(int doctorId);
}
