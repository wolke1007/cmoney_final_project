package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Modifier;

public class CommonResponse {

    private int status;
    private Object message;

    public CommonResponse() {
    }

    public CommonResponse(JsonArray json, int status) {
        this.status = status;
        this.message = (JsonArray) this.message;
        this.message = json;
    }

    public CommonResponse(JsonObject json, int status) {
//        System.out.println("json obj construct");
        this.status = status;
        this.message = (JsonObject) this.message;
        this.message = json;
    }

    public CommonResponse(String json, int status) {
        this.status = status;
        this.message = (String)this.message;
        this.message = json;
    }

    public CommonResponse(Object message, int status) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public JsonObject setMessage(JsonObject ret, String str) {
//        System.out.println("string setter");
        ret.addProperty("message", str);
        return ret;
    }

    public JsonObject setMessage(JsonObject ret, JsonArray json) {
//        System.out.println("json array setter");
        ret.add("message", json);
        return ret;
    }

    public JsonObject setMessage(JsonObject ret, JsonObject json) {
//        System.out.println("json object setter");
        ret.add("message", json);
        return ret;
    }

    public JsonObject setMessage(JsonObject ret, Object message) {
        System.out.println("object setter");
        Gson g = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create();
        ret.add("message", g.toJsonTree(message));
        return ret;
    }

    @Override
    public String toString() {
        JsonObject ret = new JsonObject();
        ret.addProperty("status", getStatus());
        String cls = this.message.getClass().getSimpleName();
        switch(cls){
            case "JsonArray":
                ret = setMessage(ret, (JsonArray) this.message);
                break;
            case "JsonObject":
                ret = setMessage(ret, (JsonObject) this.message);
                break;
            case "String":
                ret = setMessage(ret, (String) this.message);
                break;
            case "Object":
                ret = setMessage(ret, this.message);
                break;
            default:
                System.out.println("input type is: " + cls + " using object method to transform");
                ret = setMessage(ret, this.message);
                break;
        }
        return ret.toString();
    }
}