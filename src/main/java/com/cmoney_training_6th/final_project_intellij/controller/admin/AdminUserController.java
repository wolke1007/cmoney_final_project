package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.model.UserPhoto;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoDoctor;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
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

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/doctor/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewDoctor(
            HttpServletResponse response,
            @RequestBody DtoDoctor request
    ) {
        try{
            Doctor doctor = new Doctor();
            User user = userRepository.findByUsername(request.getUserName()).orElse(null);
            if(doctorRepository.findByUserId(user.getId()).orElse(null) != null &&
                    doctorRepository.findByHospitalId(request.getHospitalId()).size() > 0){
                response.setStatus(404);
                return new CommonResponse("this user already was a doctor.", 404).toString();
            }
            System.out.println("debug doctor userID:"+user.getId());
            doctor.setSkill(request.getSkill());
            doctor.setExperience(request.getExperience());
            doctor.setDoctorLicense(request.getDoctorLicense());
            doctor.setUserId(user.getId());
            doctor.setHospitalId(request.getHospitalId());
            doctorRepository.save(doctor);
            user.setRole("ROLE_DOCTOR");
            userRepository.save(user);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("NoSuchElementException: " + e.getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/staff/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewStaff(
            HttpServletResponse response,
            @RequestBody User request
    ) {
        ValidateParameter checkRole = new ValidateParameter("role", request.getRole());
        if(!checkRole.stringShouldNotBe("ROLE_ADMIN")
                .stringShouldNotBe("ROLE_DOCTOR")
                .getResult()){
            response.setStatus(404);
            return new CommonResponse("role of this regist method is wrong.",404).toString();
        }
        // 新增 User
        try {
            userRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
        return new CommonResponse("success", 200).toString();
    }

    @PutMapping(path = "/staff/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditStaffByUsername(
            HttpServletResponse response,
            @RequestBody User request
    ) {
        ValidateParameter checkRole = new ValidateParameter("role", request.getRole());
        if(!checkRole.stringShouldNotBe("ROLE_ADMIN")
                .stringShouldNotBe("ROLE_DOCTOR")
                .stringShouldBe("ROLE_STAFF")
                .getResult()){
            response.setStatus(404);
            return new CommonResponse("role of this edit method is wrong.",404).toString();
        }
        try {
            User user = userRepository.findByUsername(request.getUsername()).get();
            request.setId(user.getId());
            userRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
        return new CommonResponse("success", 200).toString();
    }

    @PutMapping(path = "/staff/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminDeleteStaffByUsernameAndId(
            HttpServletResponse response,
            @RequestBody User request
    ) {
        try {
            // id 與 username 各取一次比對 id 是否為同一個人
            User user = userRepository.findByUsername(request.getUsername()).get();
            User checkUser = userRepository.findById(request.getId()).get();
            if(user.getId() != checkUser.getId()){
                return new CommonResponse("fail, user id and user_name can not match.", 404).toString();
            }
            // 確認刪除的帳號身分是否為 STAFF
            ValidateParameter checkRole = new ValidateParameter("role", user.getRole());
            if(!checkRole.stringShouldNotBe("ROLE_ADMIN")
                    .stringShouldNotBe("ROLE_DOCTOR")
                    .stringShouldBe("ROLE_STAFF")
                    .getResult()){
                response.setStatus(404);
                return new CommonResponse("role of this delete method is wrong.",404).toString();
            }
            // 取消登入權，不真實刪除資料
            user.setActive(false);
            userRepository.save(user);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAdminInfo(@RequestHeader("Authorization") String header) {
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            Optional<User> user = userRepository.findByUsername(username);
            Gson g = new Gson();
            JsonObject json = g.toJsonTree(user).getAsJsonObject().get("value").getAsJsonObject();
            json.remove("password"); // 不能讓前端看到密碼
            json.remove("reservations");
            json.remove("doctors");
            json.remove("medicalRecords");
            json.remove("pets");
            json.remove("role");
            json.remove("active");
            int hospitalId = hospitalRepository.findByUserId(user.get().getId()).get().getId();
            json.addProperty("hospitalId", hospitalId);
            return new CommonResponse(json, 200).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("wrong token was given.", 404).toString();
        } catch (NonUniqueResultException e) {
            return new CommonResponse(" query did not return a unique result.", 500).toString();
        }
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


