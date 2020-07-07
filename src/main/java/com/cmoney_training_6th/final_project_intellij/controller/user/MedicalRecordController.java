package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.MedicalRecordRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/medical_record")
public class MedicalRecordController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping(path = "/hello")
    public String acHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/pet") // Map ONLY POST Requests
    public CommonResponse addNewUser(
            HttpServletResponse response,
            @RequestBody Pet pet
    ) throws Exception {

        try {
            MedicalRecord m = new MedicalRecord();
            Optional<MedicalRecord> test = medicalRecordRepository.findByPet_id(pet.getId());
            return new CommonResponse("Saved", 200);
        } catch (DataIntegrityViolationException e) {
            response.setStatus(400);
            return new CommonResponse("Key duplicated", 400);
        }
    }

//    @GetMapping(path="/all") // debug 用
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }

//    @PostMapping(path="/find/user/name")
//    public ErrorResponse findUserByUserName(@RequestParam String username) {
//        if(userRepository.findByUsername(username).equals(Optional.empty())){
//            return new ErrorResponse(userRepository.findByUsernameContaining(username).stream(), 200);
//        }
//        return new ErrorResponse(userRepository.findByUsernameContaining(username).stream(), 200);
//    }

    @GetMapping(path = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<MedicalRecord> findRecordById(@PathVariable(value = "id") int pet_id) {
        MedicalRecord m = new MedicalRecord();
        Optional<MedicalRecord> ret = medicalRecordRepository.findByPet_id(pet_id);
        return ret;
//        Optional<User> test = userRepository.findById(id);
//        Gson g = new Gson();
//        JsonElement je = g.toJsonTree(test).getAsJsonObject().get("value");
//        JsonObject json = (JsonObject)g.toJsonTree(test).getAsJsonObject().get("value");
//        json.remove("password");
//        json.remove("role");
//        json.add("je", je);
//        System.out.println(json);
//        JsonObject newJson = new JsonObject();
//        newJson.addProperty("status", 200);
//        newJson.add("message", json);
//        return newJson.toString();
    }

//    @PostMapping(path="/find/user/role")
//    public Iterable<User> findUsersByRole(HttpServletResponse response, @RequestParam String role) {
//        System.out.println(userRepository.findAllByRoleOrderByUsername(role));
//        return userRepository.findAllByRoleOrderByUsername(role);
//    }
//
//    @PostMapping(path="/find/user/doctor")
//    public Optional<Doctor> findDoctorByUserId(HttpServletResponse response, @RequestParam int id) {
////        System.out.println(doctorRepository.findByUserId(id));
//        return doctorRepository.findByUser_id(id);
//    }

}