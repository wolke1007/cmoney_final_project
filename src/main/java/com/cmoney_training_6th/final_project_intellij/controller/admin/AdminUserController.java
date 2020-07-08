package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.model.UserPhoto;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin")
public class AdminUserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminUserHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/new/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewDoctor(
            HttpServletResponse response,
            @RequestBody Doctor request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", password);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
//        // 新增 Doctor
        try{
            doctorRepository.save(request);
            return new CommonResponse("success", 200).toString();
        }catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e, 404).toString();
        }
    }

    @PostMapping(path = "/new/staff", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewStaff(
            HttpServletResponse response,
            @RequestBody User request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", jsonUser.getPassword());
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        // 新增 User
        try {
            userRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e, 404).toString();
        }
        return new CommonResponse("success", 200).toString();
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @GetMapping(path = "/by/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String findUserById(@RequestParam int id) {
//        Optional<User> test = userRepository.findById(id);
//        Gson g = new Gson();
//        JsonElement je = g.toJsonTree(test).getAsJsonObject().get("value");
//        JsonObject json = (JsonObject) g.toJsonTree(test).getAsJsonObject().get("value");
//        json.remove("role");
//        json.add("je", je);
//        System.out.println(json);
//        JsonObject newJson = new JsonObject();
//        newJson.addProperty("status", 200);
//        newJson.add("message", json);
//        return new CommonResponse(newJson, 200).toString();
//    }
//
//    @GetMapping(path = "/by/role", produces = MediaType.APPLICATION_JSON_VALUE) // DEBUG
//    public Iterable<User> findUsersByRole(HttpServletResponse response, @RequestParam String role) {
//        System.out.println(userRepository.findAllByRoleOrderByUsername(role));
//        return userRepository.findAllByRoleOrderByUsername(role);
//    }

//    @PersistenceContext
//    public User loadUserById(EntityManager entityManager, int id){
//        return entityManager.getReference(User.class, id);
//    }

}


