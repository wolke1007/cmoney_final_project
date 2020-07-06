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
    public String acHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PutMapping(path = "/new/member", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewUser(
            HttpServletResponse response,
            @RequestParam String social_license_id,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String name,
            @RequestParam String doctor_lincense,
            @RequestParam String role,
            @RequestParam String school,
            @RequestParam String birthday,
            @RequestParam String join_time,
            @RequestParam String address_city,
            @RequestParam String address_area,
            @RequestParam String address_line,
            @RequestParam String experience,
            @RequestParam String skill,
            @RequestParam int hospital_id
    ) {
        ValidateParameter checkPassword = new ValidateParameter("password", password);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        // 新增 User
        try {
            User u = new User();
            u.setSocial_license_id(social_license_id);
            u.setUsername(username);
            u.setPassword(password);
            u.setPhone(phone);
            u.setLast_name(name.substring(0, 1));
            u.setFirst_name(name.substring(1));
            u.setRole(role);
            u.setSchool(school);
            u.setBirthday(birthday);
            u.setJoin_time(join_time);
            u.setAddress_city(address_city);
            u.setAddress_area(address_area);
            if(address_line != null){ u.setAddress_line(address_line); }
// TODO add photo here, and return a photo_id, then add
//          photo_id
//          UserPhoto up = new UserPhoto();
//          up.setUser();
            userRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("Key duplicated", 404).toString();
        }
        // 新增 Doctor
        try{
            System.out.println("role: " + role);
            if(role.equals("ROLE_DOCTOR")){
                System.out.println("role is DOCTOR");
                Doctor d = new Doctor();
                d.setDoctor_license(doctor_lincense);
                if(experience != null){ d.setExperience(experience);}
                else{System.out.println("experience is not null");};
                if(skill != null){ d.setSkill(skill); }
                else{System.out.println("skill is not null");};
                if(hospital_id != 0){ d.setHospital(hospitalRepository.findById(hospital_id).get()); }
                else{System.out.println("hostpital is 0");};
                if(userRepository.findByUsername(username).get() != null){
                    d.setUser(userRepository.findByUsername(username).get());
                }else{System.out.println("User can't find by username:" + username);};
                System.out.println("doc repo save");
                doctorRepository.save(d);
            }
            return new CommonResponse("success, regist as role " + role, 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("Key duplicated", 404).toString();
        }
    }
//
//    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
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

