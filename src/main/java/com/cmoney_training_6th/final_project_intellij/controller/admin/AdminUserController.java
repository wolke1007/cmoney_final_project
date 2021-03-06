package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoCrewUser;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoUserDoctor;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin")
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/doctor/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewDoctor(
            HttpServletResponse response,
            @RequestBody DtoUserDoctor request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            User user = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (user != null) {
                response.setStatus(404);
                return new CommonResponse("this username is exist.", 404).toString();
            }else{
                user = new User();
            }
            user.setUsername(request.getUsername());
            user.setSocialLicenseId(request.getSocialLicenseId());
            user.setJoinTime(request.getJoinTime());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(request.getPassword());
            user.setSchool(request.getSchool());
            user.setAddressCity(request.getAddressCity());
            user.setAddressArea(request.getAddressArea());
            user.setAddressLine(request.getAddressLine());
            user.setPhone(request.getPhone());
            user.setBirthday(request.getBirthday());
            user.setRole("ROLE_DOCTOR");
            userRepository.save(user);
            user = userRepository.findByUsername(request.getUsername()).orElse(null);
            if(user == null){
                response.setStatus(404);
                return new CommonResponse("user save failed, can't get user by name:" + request.getUsername(), 404).toString();
            }
            Doctor doctor = new Doctor();
            doctor.setHospitalId(request.getHospitalId());
            doctor.setUserId(user.getId());
            doctor.setDoctorLicense(request.getDoctorLicense());
            doctor.setExperience(request.getExperience());
            doctor.setSkill(request.getSkill());
            doctorRepository.save(doctor);
            doctor = doctorRepository.findByUserId(user.getId()).orElse(null);
            if(doctor == null){
                response.setStatus(404);
                return new CommonResponse("doctor save failed, can't get doctor by id:" + user.getId(), 404).toString();
            }
            Crew crew = new Crew();
            crew.setHospitalId(doctor.getHospitalId());
            crew.setUserId(user.getId());
            crewRepository.save(crew);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("NoSuchElementException: " + e.getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/staff/new", produces = MediaType.APPLICATION_JSON_VALUE) // 員工沒有帳號，admin 幫創帳號用
    public String addNewStaffWithNoUserAccount(
            HttpServletResponse response,
            @RequestBody DtoCrewUser request
    ) {
        // TODO should refactor here, because fat controller design
        ValidateParameter checkRole = new ValidateParameter("role", request.getUser().getRole());
        if (!checkRole.stringShouldNotBe("ROLE_ADMIN")
                .stringShouldNotBe("ROLE_DOCTOR")
                .getResult()) {
            response.setStatus(404);
            return new CommonResponse("role of this regist method is wrong.", 404).toString();
        }
        // 新增 User
        try {
            userRepository.save(request.getUser());
            Crew crew = new Crew();
            crew.setHospitalId(request.getHospitalId());
            crew.setUserId(request.getUser().getId());
            if (crewRepository.findByUserIdAndHospitalId(crew.getId(), crew.getHospitalId()).orElse(null) != null) {
                return new CommonResponse("this user already is staff of this hospital.", 200).toString();
            }
            crewRepository.save(crew);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/staff/add", produces = MediaType.APPLICATION_JSON_VALUE) // 從現有 user 中新增至醫院 crew 中
    public String addCrewFromExistUserAccount(
            HttpServletResponse response,
            @RequestBody User request,
            @RequestHeader("Authorization") String header) {
        // TODO should refactor here, because fat controller design
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User userAdmin = userRepository.findByUsername(username).orElse(null);
            Hospital hospital = hospitalRepository.findByUserId(userAdmin.getId()).orElse(null);
            if (hospital == null) {
                response.setStatus(403);
                return new CommonResponse("this admin user don't own any hospital.", 403).toString();
            }
            if (crewRepository.findByUserId(request.getId()).orElse(null) != null) {
                return new CommonResponse("this user already is staff of other hospital, should delete first.", 403).toString();
            }
            Crew crew = new Crew();
            crew.setHospitalId(hospital.getId());
            crew.setUserId(request.getId());
            crewRepository.save(crew);
            User user = userRepository.findById(request.getId()).orElse(null);
            Doctor doctor = doctorRepository.findByUserId(user.getId()).orElse(null);
            if( doctor != null){
                doctor.setHospitalId(hospital.getId());
                doctorRepository.save(doctor);
            }else{
                user.setRole("ROLE_STAFF");
                userRepository.save(user);
            }
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (io.jsonwebtoken.MalformedJwtException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        } catch (StringIndexOutOfBoundsException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        }
    }

    @PostMapping(path = "/crew/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditCrewByUsername(
            HttpServletResponse response,
            @RequestBody DtoUserDoctor request
    ) {
        // TODO should refactor here, because fat controller design
        try {
            User user = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (user == null) {
                response.setStatus(404);
                return new CommonResponse("this username does not exist.", 404).toString();
            }
            if(user.getRole().equals("ROLE_USER")){
                response.setStatus(403);
                return new CommonResponse("this method is to edit crew, " +
                        " only crews of ADMIN or STAFF or DOCTOR can be edit by hospital owner.", 404).toString();
            }
            if(crewRepository.findByUserId(user.getId()).orElse(null) == null){
                response.setStatus(403);
                return new CommonResponse("this user is not crew of your hospital.", 404).toString();
            }
            user.setSocialLicenseId(request.getSocialLicenseId());
            user.setJoinTime(request.getJoinTime());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            if(!request.getPassword().equals("")){
                user.setPassword(request.getPassword());
            }
            user.setSchool(request.getSchool());
            user.setAddressCity(request.getAddressCity());
            user.setAddressArea(request.getAddressArea());
            user.setAddressLine(request.getAddressLine());
            user.setPhone(request.getPhone());
            user.setBirthday(request.getBirthday());
            userRepository.save(user);
            Doctor doctor = doctorRepository.findByUserIdAndHospitalId(user.getId(), request.getHospitalId()).orElse(null);
            if(doctor != null){
                doctor.setDoctorLicense(request.getDoctorLicense());
                doctor.setExperience(request.getExperience());
                doctor.setSkill(request.getSkill());
                doctorRepository.save(doctor);
            }
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/crew/delete", produces = MediaType.APPLICATION_JSON_VALUE) // 從醫院中除職的概念
    public String adminDeleteStaffByUsernameAndId(
            HttpServletResponse response,
            @RequestBody User request,
            @RequestHeader("Authorization") String header) {
        // TODO should refactor here, because fat controller design
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User userAdmin = userRepository.findByUsername(username).orElse(null);
            if (!userAdmin.getRole().equals("ROLE_ADMIN")) {
                response.setStatus(403);
                return new CommonResponse("reject this operation because token of this user is not ADMIN.", 403).toString();
            }
            Hospital hospital = hospitalRepository.findByUserId(userAdmin.getId()).orElse(null);
            if (hospital == null) {
                response.setStatus(403);
                return new CommonResponse("this admin user don't own any hospital.", 403).toString();
            }
            // id 與 username 各取一次比對 id 是否為同一個人
            User userCrew = userRepository.findByUsername(request.getUsername()).orElse(null);
            Crew crew = crewRepository.findByUserIdAndHospitalId(userCrew.getId(), hospital.getId()).orElse(null);
            if (crew == null) {
                return new CommonResponse(
                        "not authorized to delete this staff, who is not belong to hospital[" + hospital.getId() + "].",
                        404).toString();
            }
            // 確認刪除的帳號身分不為 ADMIN or USER
            ValidateParameter checkRole = new ValidateParameter("role", userCrew.getRole());
            if (!checkRole.stringShouldNotBe("ROLE_ADMIN")
                    .stringShouldNotBe("ROLE_USER")
                    .getResult()) {
                response.setStatus(404);
                return new CommonResponse("role of this delete method is wrong.", 404).toString();
            }
            // 刪除 staff 的概念是將該人員 role 改回 ROLE_USER 然後從 crew table 中剔除
            System.out.println("delete crew");
            crewRepository.delete(crew);
            System.out.println("delete over");
            // 如果對象是醫生，則需要從 doctor table 中將 hospital id 給改成 0
            if(userCrew.getRole().equals("ROLE_DOCTOR")){
                Doctor doctor = doctorRepository.findByUserId(userCrew.getId()).orElse(null);
                if(doctor != null){
                    doctor.setHospitalId(0);
                    doctorRepository.save(doctor);
                    // 醫生退出醫院了還是醫生，所以不用更改 user role
                }
            }else{
                // 員工預期只會待在一家醫院，如果除職則恢復成 user 身份
                userCrew.setRole("ROLE_USER");
                userRepository.save(userCrew);
            }
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getMessage(), 404).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (io.jsonwebtoken.MalformedJwtException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        } catch (StringIndexOutOfBoundsException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAdminInfo(HttpServletResponse response,
                               @RequestHeader("Authorization") String header) {
        // TODO should refactor here, because fat controller design
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
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (io.jsonwebtoken.MalformedJwtException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        } catch (StringIndexOutOfBoundsException e){
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        }
    }

}


