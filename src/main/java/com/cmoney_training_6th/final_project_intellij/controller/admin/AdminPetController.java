package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.service.PetService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.NoSuchElementException;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/pet")
public class AdminPetController {
    @Autowired
    private PetService petService;


    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addPet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.add(request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editPet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.edit(request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String deletePet(
            HttpServletResponse response,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.delete(request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllPet(HttpServletResponse response) throws Exception {
        CommonResponse ret = petService.getAll();
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "/userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findByUserId(HttpServletResponse response,
                               @PathVariable("userId") int userId) {
        CommonResponse ret = petService.getByUserId(userId);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "/hospitalId/{hospitalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findByHospitalId(HttpServletResponse response,
                                   @PathVariable("hospitalId") int hospitalId) {
        CommonResponse ret = petService.getByHospitalId(hospitalId);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

}

