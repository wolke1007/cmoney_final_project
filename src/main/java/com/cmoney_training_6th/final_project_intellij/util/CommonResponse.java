package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CommonResponse{

    private int status;
    private JsonObject newJson;

    public CommonResponse(String message, int status){
        this.status = status;
        this.newJson = new JsonObject();
        this.newJson.addProperty("status", status);
        setJsonMessage(message);
    }

    public CommonResponse(JsonObject message, int status){
        this.status = status;
        this.newJson = new JsonObject();
        this.newJson.addProperty("status", status);
        setJsonMessage(message);
    }

    public CommonResponse(JsonArray message, int status){
        this.status = status;
        this.newJson = new JsonObject();
        this.newJson.addProperty("status", status);
        setJsonMessage(message);
    }

    public void setJsonMessage(String message){
        System.out.println("message input is not JsonObject");
        this.newJson.addProperty("message", message.toString());
    }

    public void setJsonMessage(JsonObject message){
        System.out.println("message input is JsonObject");
        this.newJson.add("message", message);
    }

    public void setJsonMessage(JsonArray message){
        System.out.println("message input is JsonObject");
        this.newJson.add("message", message);
    }

    @Override
    public String toString() {
        return newJson.toString();
    }

}
