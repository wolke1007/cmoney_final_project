package com.cmoney_training_6th.final_project_intellij.services;

import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.model.Reservation;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.repos.ReservationRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Reservation> findByUsername(String username){
//        User user = userRepository.findByUsername(username).orElse(null);
//        int userId =
        return null;
    }

}
