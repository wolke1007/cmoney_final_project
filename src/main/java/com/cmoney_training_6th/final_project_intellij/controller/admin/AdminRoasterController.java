package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoRoaster;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/roaster")
public class AdminRoasterController {

    @Autowired
    private RoasterRepository roasterRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminAddRoaster(
            HttpServletResponse response,
            @RequestBody DtoRoaster request) {
        // TODO should refactor here, because fat controller design
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
            Roaster createdRoaster = roasterRepository.findByDoctorIdAndScheduleId(newRoaster.getDoctorId(), newRoaster.getScheduleId()).orElse(null);
            if(createdRoaster == null){
                return new CommonResponse("roaster which just been created can't be found.", 500).toString();
            }
            return new CommonResponse(createdRoaster.getId(), 200).toString();
        }  catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + Objects.requireNonNull(e.getRootCause()).getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/by/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetRoaster(@RequestParam(value = "doctorId")
                                          int doctorId) {
        // TODO should refactor here, because fat controller design
        List<Roaster> roasters = roasterRepository.findByDoctorId(doctorId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(roasters, "reservations");
        for (JsonElement json : arr) {
            int scheduleId = json.getAsJsonObject().get("scheduleId").getAsInt();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            Doctor doctor = doctorRepository.findById(json.getAsJsonObject().get("doctorId").getAsInt()).orElse(null);
            User user = userRepository.findById(doctor.getUserId()).orElse(null);
            json.getAsJsonObject().addProperty("doctorName", user.getLastName()+user.getFirstName());
            json.getAsJsonObject().addProperty("day", schedule.getDay());
            json.getAsJsonObject().addProperty("time", schedule.getTime());
        }
        return new CommonResponse(arr, 200).toString();
    }

    @GetMapping(path = "/by/hospital", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetRoastersByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        // TODO should refactor here, because fat controller design
        List<Roaster> roasters = roasterRepository.findByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKeys(roasters,
                Arrays.asList("scheduleId", "reservations"));
        int index = 0;
        for (Roaster roaster : roasters) {
            int scheduleId = roaster.getScheduleId();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            Doctor doctor = doctorRepository.findById(roaster.getDoctorId()).orElse(null);
            User user = userRepository.findById(doctor.getUserId()).orElse(null);
            arr.get(index).getAsJsonObject().addProperty("doctorName", user.getLastName()+user.getFirstName());
            arr.get(index).getAsJsonObject().addProperty("day", schedule.getDay());
            arr.get(index).getAsJsonObject().addProperty("time", schedule.getTime());
            index++;
        }
        return new CommonResponse(arr, 200).toString();
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditReservation(HttpServletResponse response,
                                       @RequestBody DtoRoaster request) {
        // TODO should refactor here, because fat controller design
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
        // TODO should refactor here, because fat controller design
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
