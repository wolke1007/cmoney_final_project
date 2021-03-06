package com.cmoney_training_6th.final_project_intellij.controller;

import com.cmoney_training_6th.final_project_intellij.authentication.AuthenticationAdminRequest;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.dao.CrewRepository;
import com.cmoney_training_6th.final_project_intellij.dao.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.dao.UserRepository;
import com.cmoney_training_6th.final_project_intellij.implenment.MyUserDetailsServiceImpl;
import com.cmoney_training_6th.final_project_intellij.authentication.AuthenticationRequest;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.cmoney_training_6th.final_project_intellij.util.ValidateParameter;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController
public class AuthencateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        // TODO should refactor here, because fat controller design
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new CommonResponse("Incorrect username or password", 404).toString();
//            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        JsonObject json = new JsonObject();
        json.addProperty("token", jwt);
        return new CommonResponse(json, 200).toString();
    }

    @RequestMapping(value = "/login/admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    public String createAdminAuthenticationToken(
            HttpServletResponse response,
            @RequestBody AuthenticationAdminRequest authenticationRequest) throws Exception {
        // TODO should refactor here, because fat controller design
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new CommonResponse("Incorrect username or password", 404).toString();
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        try {
            Hospital hospital = hospitalRepository.findByUniSerialId(authenticationRequest.getUniSerialId()).get();
            User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);
            int userId = user.getId();
            int hospitalId = hospital.getId();
            System.out.println("userId:" + userId + " hosp id:" +hospitalId );
            if(crewRepository.findByUserIdAndHospitalId(userId, hospitalId).orElse(null) == null){
                response.setStatus(403);
                return new CommonResponse("this user is not crew of this hospital.", 403).toString();
            }
            JsonObject json = new JsonObject();
            json.addProperty("token", jwt);
            json.addProperty("hospital_id", hospital.getId());
            return new CommonResponse(json, 200).toString();
        }catch(NoSuchElementException e){
            return new CommonResponse(
                    "can not found hospital with uniSerialId: " + authenticationRequest.getUniSerialId(),
                    404).toString();
        }
    }

    @PostMapping(path = "/regist/user", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewUser(
            HttpServletResponse response,
            @RequestBody User request
    ) throws Exception {
        // TODO should refactor here, because fat controller design
        ValidateParameter checkRole = new ValidateParameter("role", request.getRole());
        if(!checkRole.stringShouldBe("ROLE_USER")
                .getResult()){
            response.setStatus(404);
            return new CommonResponse("role of this regist method should be ROLE_USER",404).toString();
        }
        try {
            User n = new User();
            n.setUsername(request.getUsername());
            n.setPassword(request.getPassword());
            n.setSocialLicenseId(request.getSocialLicenseId());
            n.setJoinTime(request.getJoinTime());
            n.setRole(request.getRole());
            userRepository.save(n);
            return new CommonResponse("Saved", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/regist/admin", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewAdmin(
            HttpServletResponse response,
            @RequestBody User jsonUser
    ) throws Exception {
        // TODO should refactor here, because fat controller design
        ValidateParameter checkRole = new ValidateParameter("role", jsonUser.getRole());
        if(!checkRole.stringShouldBe("ROLE_ADMIN")
                .getResult()){
            response.setStatus(404);
            return new CommonResponse("role of this regist method should be ROLE_ADMIN",404).toString();
        }
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
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

}
