package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Reservation;
import com.cmoney_training_6th.final_project_intellij.dao.ReservationRepository;
import com.cmoney_training_6th.final_project_intellij.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
