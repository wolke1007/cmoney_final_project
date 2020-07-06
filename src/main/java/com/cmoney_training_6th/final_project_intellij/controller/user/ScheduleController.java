package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Schedule;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.ScheduleRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/schedule")
public class ScheduleController {
//    @Autowired // This means to get the bean called userRepository
//    // Which is auto-generated by Spring, we will use it to handle the data
//    private UserRepository userRepository;
//
//    @Autowired
//    private ScheduleRepository scheduleRepository;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String acHello(@RequestParam String utf8) {
//        return "account hello 中文測試" + utf8;
//    }
//
//    @GetMapping(path = "/")
//    public String getScheuldeIdByDayAndTime(
//            @RequestParam(value = "day") String day,
//            @RequestParam(value = "time") String time
//    )  {
//        return new CommonResponse("test", 200).toString();
//    }
//
//    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @PostMapping(path = "/find/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String findUserById(@RequestParam int id) throws JSONException, JsonProcessingException {
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
//    @PostMapping(path = "/find/role", produces = MediaType.APPLICATION_JSON_VALUE) // DEBUG
//    public Iterable<User> findUsersByRole(HttpServletResponse response, @RequestParam String role) {
//        System.out.println(userRepository.findAllByRoleOrderByUsername(role));
//        return userRepository.findAllByRoleOrderByUsername(role);
//    }

}