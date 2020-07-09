package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CommonResponse {

    private int statusCode;
    private Object message;

    public CommonResponse() {
    }

    public CommonResponse(JsonObject json, int statusCode) {
        this.statusCode = statusCode;
        this.message = (JsonObject) this.message;
        this.message = json;
    }

    public CommonResponse(String json, int statusCode) {
        this.statusCode = statusCode;
        this.message = (String)this.message;
        this.message = json;
    }

    public CommonResponse(Object message, int statusCode) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public JsonObject setMessage(JsonObject ret, String str) {
        ret.addProperty("message", str);
        return ret;
    }

    public JsonObject setMessage(JsonObject ret, JsonObject json) {
        ret.add("message", json);
        return ret;
    }

    public JsonObject setMessage(JsonObject ret, Object message) {
        Gson g = new Gson();
        ret.add("message", g.toJsonTree(message).getAsJsonObject());
        return ret;
    }

    @Override
    public String toString() {
        JsonObject ret = new JsonObject();
        ret.addProperty("status", getStatusCode());
        if (this.message.getClass().equals(String.class)){
            ret = setMessage(ret, (String)this.message);
        }else{
            ret = setMessage(ret, this.message);
        }
        return ret.toString();
    }
}