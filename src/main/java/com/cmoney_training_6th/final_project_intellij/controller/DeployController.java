package com.cmoney_training_6th.final_project_intellij.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeployController {

    @Value("${build_version}")
    private String build_version;

    @GetMapping(path = "/version")
    public @ResponseBody String getBuildVersion(){
        return "API version: " + build_version;
    }
}
