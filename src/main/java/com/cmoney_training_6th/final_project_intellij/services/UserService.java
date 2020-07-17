package com.cmoney_training_6th.final_project_intellij.services;

import com.cmoney_training_6th.final_project_intellij.model.Crew;
import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.CrewRepository;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public JsonObject findCrewByHospitalId(int hospitalId) {
        JsonObject ret = new JsonObject();
        JsonIter ji;
        Gson g = new Gson();
        ret.add("doctors", new JsonArray());
        ret.add("staffs", new JsonArray());
        List<Crew> crews = crewRepository.findByHospitalId(hospitalId);
        for (Crew crew : crews) {
            ji = new JsonIter();
            Doctor doctor = doctorRepository.findByUserId(crew.getUserId()).orElse(null);
            User user = userRepository.findById(crew.getUserId()).orElse(null);
            if (doctor != null) {
                JsonObject doctorJson = ji.objIntoJsonWithoutKeys(doctor, Arrays.asList("roasters"));
                //TODO 要加入 doctor 的 user info
                JsonObject userJson = ji.objIntoJsonWithKeys(user, Arrays.asList("id",
                        "socialLicenseId", "joinTime", "firstName",
                        "lastName", "school", "addressCity",
                        "addressArea", "addressLine", "phone",
                        "birthday", "username", "role",
                        "userPhotos"));
                JsonObject addTwoJson = ji.jsonConcact(doctorJson, userJson);
                System.out.println("DEBUG addTwoJson:" + addTwoJson);
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
}
