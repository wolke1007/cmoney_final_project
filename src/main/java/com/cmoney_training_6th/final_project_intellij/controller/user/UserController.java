package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/user")
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

//    @Autowired
//    private DoctorRepository doctorRepository;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String acHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/regist", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewUser(
            HttpServletResponse response,
            @RequestBody User jsonUser
    ) throws Exception {
        ValidateParameter checkPassword = new ValidateParameter("password", jsonUser.getPassword());
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        try {
            User n = new User();
            n.setUsername(jsonUser.getUsername());
            n.setPassword(jsonUser.getPassword());
            n.setJoinTime(jsonUser.getJoinTime());
            n.setRole(jsonUser.getRole());
            userRepository.save(n);
            return new CommonResponse("Saved", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("Key duplicated", 404).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // DEBUG
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/by/id", produces = MediaType.APPLICATION_JSON_VALUE) // DEBUG
    public String findUserById(@RequestParam int id) {
        Optional<User> test = userRepository.findById(id);
        Gson g = new Gson();
        JsonElement je = g.toJsonTree(test).getAsJsonObject().get("value");
        JsonObject json = (JsonObject) g.toJsonTree(test).getAsJsonObject().get("value");
        json.remove("role");
        json.add("je", je);
        System.out.println(json);
        JsonObject newJson = new JsonObject();
        newJson.addProperty("status", 200);
        newJson.add("message", json);
        return new CommonResponse(newJson, 200).toString();
    }

}