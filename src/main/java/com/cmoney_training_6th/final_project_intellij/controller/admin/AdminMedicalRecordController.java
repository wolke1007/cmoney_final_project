package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import com.cmoney_training_6th.final_project_intellij.dao.MedicalRecordRepository;
import com.cmoney_training_6th.final_project_intellij.dao.PetRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/medical_record")
public class AdminMedicalRecordController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PetRepository petRepository;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewMedicalRecord(
            HttpServletResponse response,
            @RequestBody MedicalRecord request
    ) {
        // TODO should refactor here, because fat controller design
        try{
            if(medicalRecordRepository.findByUserIdAndPetIdAndHospitalId(request.getUserId(),
                    request.getPetId(),
                    request.getHospitalId()).orElse(null) != null){
                response.setStatus(404);
                return new CommonResponse("medical record already exist.", 404).toString();
            }
            medicalRecordRepository.save(request);
            return new CommonResponse("success", 200).toString();
        }catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editMedicalRecord(
            HttpServletResponse response,
            @RequestBody MedicalRecord request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getId()).orElse(null);
            if(medicalRecord == null){
                response.setStatus(404);
                return new CommonResponse("medical record " + request.getId() + " not found", 404).toString();
            }
            medicalRecord.setCreateDate(request.getCreateDate());
            medicalRecord.setHospitalId(request.getHospitalId());
            medicalRecord.setPetId(request.getPetId());
            medicalRecord.setUserId(request.getUserId());
            medicalRecordRepository.save(medicalRecord);
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
    public String deleteMedicalRecord(
            HttpServletResponse response,
            @RequestBody MedicalRecord request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getId()).get();
            medicalRecordRepository.delete(medicalRecord);
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
    public String getAllMedicalRecords(HttpServletResponse response) {
        try{
            List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
            JsonIter ji = new JsonIter();
            return new CommonResponse(ji.listIntoArray(medicalRecords), 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public String getMedicalRecordByUserIdAndPetId(HttpServletResponse response,
                                                   @RequestParam int userId,
                                                   @RequestParam int petId) {
        // TODO should refactor here, because fat controller design
        try {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByUserIdAndPetId(userId, petId);
            return new CommonResponse(medicalRecord.get(), 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("cant find data of this userId or petId.", 404).toString();
        }
    }


}


