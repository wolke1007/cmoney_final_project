package com.cmoney_training_6th.final_project_intellij.controller;

import com.cmoney_training_6th.final_project_intellij.AuthenticationAdminRequest;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.repos.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.services.MyUserDetailsService;
import com.cmoney_training_6th.final_project_intellij.AuthenticationRequest;
import com.cmoney_training_6th.final_project_intellij.AuthenticationResponse;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class AuthencateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

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
    public String createAdminAuthenticationToken(@RequestBody AuthenticationAdminRequest authenticationRequest) throws Exception {

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
            JsonObject json = new JsonObject();
            json.addProperty("token", jwt);
            json.addProperty("hospital_id", hospital.getUniSerialId());
            return new CommonResponse(json, 200).toString();
        }catch(NoSuchElementException e){
            return new CommonResponse(
                    "can not found hospital with uniSerialId: " + authenticationRequest.getUniSerialId(),
                    404).toString();
        }
    }

}
