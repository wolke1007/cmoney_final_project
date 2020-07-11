package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.services.DoctorService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.List;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/reservation")
public class AdminReservationController {
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

//    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
//    public String addNewHospital(
//            HttpServletResponse response,
//            @RequestBody Hospital request
//    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", password);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
//        // 新增 Doctor
//        try {
//            hospitalRepository.save(request);
//            return new CommonResponse("success", 200).toString();
//        } catch (DataIntegrityViolationException e) {
//            response.setStatus(404);
//            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
//        }
//    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllReservationsByHostpitalId(@RequestParam(value = "hospital_id")
                                                       int hospitalId) {
        List<Reservation> reservations = reservationRepository.findReservationByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");

        for(Reservation res : reservations){
            int roaId = res.getRoasterId();
            Roaster roaster = roasterRepository.findById(roaId).get();
            int scheduleId = roaster.getScheduleId();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            String time = schedule.getDay() + " " + schedule.getTime();
            for(JsonElement je : arr){
                je.getAsJsonObject().addProperty("time", time);
            }
        }

        return new CommonResponse(arr, 200).toString();
    }
}
