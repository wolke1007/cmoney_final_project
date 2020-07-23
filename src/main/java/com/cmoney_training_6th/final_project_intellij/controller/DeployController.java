package com.cmoney_training_6th.final_project_intellij.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeployController {

    @Value("${build_number}")
    private String buildNumber;

    @GetMapping(path = "/build_number")
    public @ResponseBody String getBuildVersion(){
        return "build number: " + buildNumber;
    }
}
