package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.jpa.repository.JpaRepository;

public class GsonIterator {

    private JpaRepository targetRepo;
    private JsonObject json;

    public GsonIterator(JpaRepository target){
        this.targetRepo = target;
        this.json = new JsonObject();
    }
//
//    public JsonObject analyz(){
//
//    }

}
