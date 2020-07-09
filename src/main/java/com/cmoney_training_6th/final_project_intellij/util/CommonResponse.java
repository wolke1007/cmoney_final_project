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

    public CommonResponse(JsonArray json, int statusCode) {
        this.statusCode = statusCode;
        this.message = (JsonArray) this.message;
        this.message = json;
    }

    public CommonResponse(JsonObject json, int statusCode) {
//        System.out.println("json obj construct");
        this.statusCode = statusCode;
        this.message = (JsonObject) this.message;
        this.message = this.message.getClass();
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
//        System.out.println("object setter");
        Gson g = new Gson();
        ret.add("message", g.toJsonTree(message).getAsJsonObject());
        return ret;
    }

    @Override
    public String toString() {
        JsonObject ret = new JsonObject();
        ret.addProperty("status", getStatusCode());
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
                ret = setMessage(ret, "not supported data type: " + cls);
                break;
        }
        return ret.toString();
    }
}