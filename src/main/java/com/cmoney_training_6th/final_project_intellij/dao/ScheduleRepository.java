package com.cmoney_training_6th.final_project_intellij.dao;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import com.cmoney_training_6th.final_project_intellij.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByDayAndTime(String day, String time);
}
