package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Reservation;
import com.cmoney_training_6th.final_project_intellij.model.Roaster;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.ReservationRepository;
import com.cmoney_training_6th.final_project_intellij.repos.RoasterRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/user/hospital")
public class HospitalController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private RoasterRepository roasterRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String acHello() {
        JsonObject newJson = new JsonObject();
        newJson.addProperty("new json test", 123456789);
        return new CommonResponse("heeehehehee", 200).toString();
    }

    @GetMapping(path = "/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewUser(@RequestParam(value = "hospital_id")
                                     int hospital_id) {
        JsonObject retJson = new JsonObject();
        Gson g = new Gson();
        JsonObject json;
        JsonObject doctorJson;
        JsonObject reservationJson;
        int cnt = 0;
        System.out.println("hostpital_id [" + hospital_id + "]");
        List<Doctor> doctor = doctorRepository.findByHospital_id(hospital_id);
        List<Roaster> roasters;
        try {
            for (Doctor doc : doctor) {
                doctorJson = new JsonObject();
                json = (JsonObject) g.toJsonTree(doc).getAsJsonObject();
                doctorJson.addProperty("doctor_id", json.get("id").toString());
                doctorJson.addProperty("first_name", json.getAsJsonObject("user").get("first_name").getAsString());
                doctorJson.addProperty("last_name", json.getAsJsonObject("user").get("last_name").getAsString());
                roasters = roasterRepository.findByDoctor_id(json.get("id").getAsInt());
                JsonObject reservationOfDoctor = new JsonObject();
                // append 班表
                int res_cnt = 0;
                for (Roaster roaster : roasters) {
                    JsonObject tmp = (JsonObject) g.toJsonTree(roaster).getAsJsonObject();
                    tmp.remove("doctor");
                    reservationOfDoctor.add(Integer.toString(res_cnt++), tmp);
                }
                doctorJson.add("roasters", reservationOfDoctor);
                doctorJson.addProperty("skill", json.get("skill").getAsString());
                doctorJson.addProperty("experience", json.get("experience").getAsString());
                retJson.add("doctor_" + Integer.toString(cnt++), doctorJson);
            }
        } catch (NullPointerException e) {
            return new CommonResponse("Doctor's user table some field is null cause Null exception", 404).toString();
        }
        return new CommonResponse(retJson, 200).toString();
    }

    @GetMapping(path = "/all") // debug 用
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @PostMapping(path="/find/user/name")
//    public ErrorResponse findUserByUserName(@RequestParam String username) {
//        if(userRepository.findByUsername(username).equals(Optional.empty())){
//            return new ErrorResponse(userRepository.findByUsernameContaining(username).stream(), 200);
//        }
//        return new ErrorResponse(userRepository.findByUsernameContaining(username).stream(), 200);
//    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @PostMapping(path = "/find/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findUserById(@RequestParam int id) throws JSONException {
        Optional<User> test = userRepository.findById(id);
        Gson g = new Gson();
        JsonElement je = g.toJsonTree(test).getAsJsonObject().get("value");
        JsonObject json = (JsonObject) g.toJsonTree(test).getAsJsonObject().get("value");
        json.remove("password");
        json.remove("role");
        json.add("je", je);
        System.out.println(json);
        JsonObject newJson = new JsonObject();
        newJson.addProperty("status", 200);
        newJson.add("message", json);
        newJson.add("message", json);
        return newJson.toString();
    }

    @PostMapping(path = "/find/role")
    public Iterable<User> findUsersByRole(HttpServletResponse response, @RequestParam String role) {
        System.out.println(userRepository.findAllByRoleOrderByUsername(role));
        return userRepository.findAllByRoleOrderByUsername(role);
    }

    @PostMapping(path = "/find/doctor")
    public Optional<Doctor> findDoctorByUserId(HttpServletResponse response, @RequestParam int id) {
//        System.out.println(doctorRepository.findByUserId(id));
        return doctorRepository.findByUser_id(id);
    }

}