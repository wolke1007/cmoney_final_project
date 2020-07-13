package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoReservation;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoRoaster;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.services.DoctorService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/roaster")
public class AdminRoasterController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private RoasterRepository roasterRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminAddRoaster(
            HttpServletResponse response,
            @RequestBody DtoRoaster request) {
        try {
            Doctor doctor = doctorRepository.findById(request.getDoctorId()).get();
            Schedule schedule = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).get();
            Roaster roaster = new Roaster();
            roaster.setDoctorId(doctor.getId());
            roaster.setScheduleId(schedule.getId());
            roasterRepository.save(roaster);
            return new CommonResponse("success", 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetRoaster(@RequestParam(value = "doctorId")
                                                       int doctorId) {
        List<Roaster> roasters = roasterRepository.findByDoctorId(doctorId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(roasters, "reservations");
        for(JsonElement json : arr){
            int scheduleId = json.getAsJsonObject().get("scheduleId").getAsInt();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            String time = schedule.getDay() + " " + schedule.getTime();
            json.getAsJsonObject().addProperty("time", time);
            json.getAsJsonObject().remove("scheduleId");
        }
        return new CommonResponse(arr, 200).toString();
    }

    @PutMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditReservation(@RequestBody DtoRoaster request) {
        Roaster roaster = roasterRepository.findById(request.getId()).get();
        int scheduleId = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).get().getId();
        roaster.setScheduleId(scheduleId);
        roaster.setDoctorId(request.getDoctorId());
        roasterRepository.save(roaster);
        return new CommonResponse("success", 200).toString();
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminDeleteRoaster(@RequestBody DtoRoaster request) {
        Roaster roaster = roasterRepository.findById(request.getId()).get();
        roasterRepository.delete(roaster);
        return new CommonResponse("success", 200).toString();
    }
}
