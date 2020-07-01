package com.cmoney_training_6th.final_project_intellij.controller;

import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/account") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @GetMapping(path = "/hello")
    public @ResponseBody String acHello(@RequestParam String social_license_id){
        return "account hello" + social_license_id;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (
            @RequestParam String social_license_id,
            @RequestParam String join_time,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String password,
            @RequestParam String address_city,
            @RequestParam String address_area,
            @RequestParam String address_line,
            @RequestParam String phone,
            @RequestParam String username,
            @RequestParam String role) throws Exception
    {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
//        Check checkUsername = new Check(first_name);
//        Check checkEmail = new Check(email);
        Check checkPassword = new Check(password);
//        if(!checkUsername.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            return checkUsername.toString();
//        }
//        if(!checkEmail.strLongerThan(50)
//                .strShorterThan(0)
//                .getResult()){
//            return checkEmail.toString();
//        }
        if(!checkPassword.strLongerThan(50)
                .strShorterThan(0)
                .getResult()){
            return checkPassword.toString();
        }
        try {
            User n = new User();
            n.setSocial_license_id(social_license_id);
            n.setJoin_time(join_time);
            n.setFirst_name(first_name);
            n.setLast_name(last_name);
            n.setPassword(password);
            n.setAddress_area(address_area);
            n.setAddress_city(address_city);
            n.setAddress_line(address_line);
            n.setPhone(phone);
            n.setUsername(username);
            n.setRole(role);
            userRepository.save(n);
            return "Saved";
        }catch (DataIntegrityViolationException e) {
            return "Key duplicated";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    private class Check {

        private boolean result;
        private String str;
        private String resultMsg;

        public Check(String str){
            this.result = true;
            this.str = str;
            this.resultMsg = "";
        }

        public Check strLongerThan(int max){
            if(this.str.length() > max){
                this.result = false;
                System.out.println("max:" + max);
                System.out.println("this.str.length():" + this.str.length());
                this.resultMsg+=" longer than " + String.valueOf(max);
            }
            return this;
        }

        public Check strShorterThan(int min){
            if(this.str.length() < min){
                this.result = false;
                this.resultMsg+=" shorter than " + String.valueOf(min);
            }
            return this;
        }

        public boolean getResult(){
            return this.result;
        }

        @Override
        public String toString(){
            return this.str + this.resultMsg;
        }

    }
}