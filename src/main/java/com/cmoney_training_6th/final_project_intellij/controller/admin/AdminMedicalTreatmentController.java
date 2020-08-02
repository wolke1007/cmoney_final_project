package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/medical_treatment")
public class AdminMedicalTreatmentController {
    @Autowired
    private MedicalTreatmentRepository medicalTreatmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewMedcalTreatment(
            HttpServletResponse response,
            @RequestBody MedicalTreatment request
    ) {
        try{
            medicalTreatmentRepository.save(request);
            return new CommonResponse("success", 200).toString();
        }catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editMedcalTreatment(
            HttpServletResponse response,
            @RequestBody MedicalTreatment request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            MedicalTreatment medicalTreatment = medicalTreatmentRepository.findById(request.getId()).get();
            medicalTreatment.setDescription(request.getDescription());
            medicalTreatment.setDate(request.getDate());
            medicalTreatmentRepository.save(medicalTreatment);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String deleteMedcalTreatment(
            HttpServletResponse response,
            @RequestBody MedicalTreatment request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            MedicalTreatment target = medicalTreatmentRepository.findById(request.getId()).get();
            medicalTreatmentRepository.delete(target);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public String getAllMedcalItems() {
        JsonArray jsonArr = new JsonArray();
        Gson g = new Gson();
        for(MedicalTreatment mt : medicalTreatmentRepository.findAll()){
            jsonArr.add(g.toJsonTree(mt));
        }
        return new CommonResponse(jsonArr, 200).toString();
    }

}


