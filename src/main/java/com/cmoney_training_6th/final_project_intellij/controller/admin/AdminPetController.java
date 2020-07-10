package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/pet")
public class AdminPetController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewPet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", username);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        try {
//            Gson g = new Gson();
//            System.out.println(g.toJsonTree(pet).getAsJsonObject());
            petRepository.save(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PutMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editPet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", username);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        try {
            petRepository.findById(request.getId()).get();
            petRepository.save(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String deletePet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
//        ValidateParameter checkPassword = new ValidateParameter("password", username);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        try {
            petRepository.findById(request.getId()).get();
            petRepository.delete(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllPet() throws Exception {
//        ValidateParameter checkPassword = new ValidateParameter("password", username);
//        if(!checkPassword.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            response.setStatus(400);
//            return new CommonResponse(checkPassword,400);
//        }
        Pet p = new Pet();
        List<Pet> pets = petRepository.findAll();
        List<User> users = userRepository.findAll();
        JsonObject json = new JsonObject();
        Gson g = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create();
        JsonArray petArr = new JsonArray();
        for (Pet pet : pets) {
            JsonObject petJson = g.toJsonTree(pet).getAsJsonObject();
            int user_id = petJson.get("userId").getAsInt();
            User user = userRepository.findById(user_id).get();
            String username = user.getUsername();
            String phone = user.getPhone();
            String lastName = user.getLastName();
            String firstName = user.getFirstName();
            petJson.addProperty("owner_email", username);
            petJson.addProperty("owner_phone", phone);
            petJson.addProperty("owner_name", lastName + firstName);
            petArr.add(petJson);
        }
        json.add("pet_list", petArr);
        return new CommonResponse(json, 200).toString();
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

