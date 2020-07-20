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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/roaster")
public class AdminRoasterController {

    @Autowired
    private RoasterRepository roasterRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminAddRoaster(
            HttpServletResponse response,
            @RequestBody DtoRoaster request) {
        try {
            Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElse(null);
            if(doctor == null){
                response.setStatus(404);
                return new CommonResponse("this doctor id" + request.getDoctorId() + " does not exist.", 404).toString();
            }
            Schedule schedule = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).orElse(null);
            if (schedule == null) {
                response.setStatus(404);
                return new CommonResponse("this schedule with [" + request.getDay()
                        + " " + request.getTime() + "] does not exist.", 404).toString();
            }
            Roaster roaster = roasterRepository.findByDoctorIdAndScheduleId(doctor.getId(), schedule.getId()).orElse(null);
            if (roaster != null) {
                response.setStatus(404);
                return new CommonResponse("fail, this roaster already exist", 404).toString();
            }
            Roaster newRoaster = new Roaster();
            newRoaster.setDoctorId(doctor.getId());
            newRoaster.setScheduleId(schedule.getId());
            roasterRepository.save(newRoaster);
            return new CommonResponse("success", 200).toString();
        }  catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/by/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetRoaster(@RequestParam(value = "doctorId")
                                          int doctorId) {
        List<Roaster> roasters = roasterRepository.findByDoctorId(doctorId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(roasters, "reservations");
        for (JsonElement json : arr) {
            int scheduleId = json.getAsJsonObject().get("scheduleId").getAsInt();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
//            String time = schedule.getDay() + " " + schedule.getTime();
//            json.getAsJsonObject().addProperty("time", time);
//            json.getAsJsonObject().remove("scheduleId");
            json.getAsJsonObject().addProperty("day", schedule.getDay());
            json.getAsJsonObject().addProperty("time", schedule.getTime());
        }
        return new CommonResponse(arr, 200).toString();
    }

    @GetMapping(path = "/by/hospital", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetRoastersByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        List<Roaster> roasters = roasterRepository.findByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKeys(roasters,
                Arrays.asList("scheduleId", "reservations"));
        for (Roaster roaster : roasters) {
            int roaId = roaster.getId();
            int scheduleId = roaster.getScheduleId();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
//            String time = schedule.getDay() + " " + schedule.getTime();
//            json.getAsJsonObject().addProperty("time", time);
//            json.getAsJsonObject().remove("scheduleId");
            for (JsonElement je : arr) {
//                je.getAsJsonObject().addProperty("time", time);
                je.getAsJsonObject().addProperty("day", schedule.getDay());
                je.getAsJsonObject().addProperty("time", schedule.getTime());
            }
        }
        return new CommonResponse(arr, 200).toString();
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditReservation(HttpServletResponse response,
                                       @RequestBody DtoRoaster request) {
        try {
            Roaster roaster = roasterRepository.findById(request.getId()).orElse(null);
            if (roaster == null) {
                response.setStatus(404);
                return new CommonResponse("this roaster id" + request.getId() + " does not exist.", 404).toString();
            }
            Schedule schedule = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).orElse(null);
            if (schedule == null) {
                response.setStatus(404);
                return new CommonResponse("this schedule with [" + request.getDay()
                        + " " + request.getTime() + "] does not exist.", 404).toString();
            }
            roaster.setScheduleId(schedule.getId());
            roaster.setDoctorId(request.getDoctorId());
            roasterRepository.save(roaster);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminDeleteRoaster(HttpServletResponse response,
                                     @RequestBody DtoRoaster request) {
        try {
            Roaster roaster = roasterRepository.findById(request.getId()).orElse(null);
            if (roaster == null) {
                response.setStatus(404);
                return new CommonResponse("this roaster id" + request.getId() + " does not exist.", 404).toString();
            }

            roasterRepository.delete(roaster);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }
}
