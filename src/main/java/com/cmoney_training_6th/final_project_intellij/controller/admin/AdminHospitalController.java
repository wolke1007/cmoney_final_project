package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserPhotoRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.services.DoctorService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/hospital")
public class AdminHospitalController {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorService doctorService;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewHospital(
            HttpServletResponse response,
            @RequestBody Hospital request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", password);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
//        // 新增 Doctor
        try {
            Optional<Hospital> hospital = hospitalRepository.findByUserId(request.getUserId());
            if(hospital.get().equals(Hospital.class)){
                int ownerId = hospital.get().getUserId();
                new CommonResponse("user already have a hospital:" + ownerId, 200).toString();
            }
            hospitalRepository.save(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

//    @GetMapping(path = "/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    @GetMapping(path = "/crews", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getDoctorDetailByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        JsonObject json = new JsonObject();
        JsonArray doctorArr = new JsonArray();
        JsonIter ji = new JsonIter();
        List<Doctor> doctors = doctorService.findByHospitalId(hospitalId);
        doctorArr = ji.listIntoArrayWithKeys(doctors, Arrays.asList("id", "name", "skill", "experience"));
        json.add("doctors", doctorArr);
        return new CommonResponse(json, 200).toString();
    }
}
