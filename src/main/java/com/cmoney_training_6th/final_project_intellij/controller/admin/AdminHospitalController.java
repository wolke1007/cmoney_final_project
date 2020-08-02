package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Crew;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.implenment.DoctorServiceImpl;
import com.cmoney_training_6th.final_project_intellij.service.DoctorService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/hospital")
public class AdminHospitalController {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewHospital(
            HttpServletResponse response,
            @RequestBody Hospital request
    ) {
        try {
            Crew crew = new Crew();
            Hospital hospital = hospitalRepository.findByUserId(request.getUserId()).orElse(null);
            if(hospital != null){
                int ownerId = hospital.getUserId();
                new CommonResponse("user already have a hospital:" + ownerId, 200).toString();
            }
            hospitalRepository.save(request);
            if(crewRepository.findByUserIdAndHospitalId(hospital.getUserId(),
                    hospital.getId()).orElse(null) != null){
                new CommonResponse("user already is crew of this hospital", 200).toString();
            }
            crew.setUserId(hospital.getUserId());
            crew.setHospitalId(hospital.getId());
            crewRepository.save(crew);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/all_crew", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllCrewByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        JsonObject json;
        Gson g = new Gson();
        json = g.toJsonTree(userService.getAllCrewByHospitalId(hospitalId)).getAsJsonObject();
        return new CommonResponse(json, 200).toString();
    }
}
