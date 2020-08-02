package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.CrewRepository;
import com.cmoney_training_6th.final_project_intellij.dao.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.dao.UserRepository;
import com.cmoney_training_6th.final_project_intellij.model.Crew;
import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.service.DoctorService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    public void add(int hospitalId) {

    }

    @Override
    public void edit(int doctorId) {

    }

    @Override
    public void delete(int doctorId) {

    }

    @Override
    public JsonObject getAllCrewByHospitalId(int hospitalId) {
        JsonObject ret = new JsonObject();
        JsonIter ji;
        ret.add("doctors", new JsonArray());
        ret.add("staffs", new JsonArray());
        List<Crew> crews = crewRepository.findByHospitalId(hospitalId);
        for (Crew crew : crews) {
            ji = new JsonIter();
            Doctor doctor = doctorRepository.findByUserId(crew.getUserId()).orElse(null);
            User user = userRepository.findById(crew.getUserId()).orElse(null);
            if (doctor != null) {
                JsonObject doctorJson = ji.objIntoJsonWithoutKeys(doctor, Arrays.asList("roasters"));
                JsonObject userJson = ji.objIntoJsonWithKeys(user, Arrays.asList("id",
                        "socialLicenseId", "joinTime", "firstName",
                        "lastName", "school", "addressCity",
                        "addressArea", "addressLine", "phone",
                        "birthday", "username", "role",
                        "userPhotos"));
                JsonObject addTwoJson = ji.jsonConcact(doctorJson, userJson);
                // 客製 json，將 doctorId 改成 userId
                int userId = addTwoJson.get("userId").getAsInt();
                int doctorId = addTwoJson.get("id").getAsInt();
                addTwoJson.remove("id");
                addTwoJson.remove("userId");
                addTwoJson.addProperty("id", userId);
                addTwoJson.addProperty("doctorId", doctorId);
                ret.get("doctors").getAsJsonArray().add(addTwoJson);
            } else {
                ret.get("staffs").getAsJsonArray().add(ji.objIntoJsonWithKeys(user,
                        Arrays.asList("id", "socialLicenseId", "joinTime",
                                "firstName", "lastName", "school", "addressCity",
                                "addressArea", "addressLine", "phone",
                                "birthday", "username", "role",
                                "userPhotos")));
            }
        }
        return ret;
    }

    @Override
    public boolean isExist(String jwt) {
        String token = jwt.substring(7);
        if (jwt == null || !jwtTokenUtil.validateJwtToken(token)) {
            return false;
        }
        String username = jwtTokenUtil.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            return false;
        }
        return true;
    }
}
