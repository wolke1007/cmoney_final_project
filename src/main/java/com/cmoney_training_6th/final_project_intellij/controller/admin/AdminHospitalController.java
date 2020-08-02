package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.service.HospitalService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/hospital")
public class AdminHospitalController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewHospital(
            HttpServletResponse response,
            @RequestBody Hospital request
    ) {
        CommonResponse ret = hospitalService.add(request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "/all_crew", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllCrewByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        JsonObject json = userService.getAllCrewByHospitalId(hospitalId);
        return new CommonResponse(json, 200).toString();
    }
}
