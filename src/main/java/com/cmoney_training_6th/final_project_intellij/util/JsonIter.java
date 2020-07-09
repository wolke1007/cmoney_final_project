package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonIter <T>{

    private JsonArray arr;
    private JsonObject json;
    private T obj;

    public JsonIter(){
        this.arr = new JsonArray();
    }
//
//    public JsonIter(JsonObject json, T obj){
//        this.json = json;
//        this.obj = obj;
//        this.arr = new JsonArray();
//    }

    public JsonArray listInToArray(List<T> list){
        Gson g =  new Gson();
        for(T obj : list){
            this.arr.add((JsonObject) g.toJsonTree(obj).getAsJsonObject());
        }
        return this.arr;
    }

    public JsonArray listIntoArrayWithoutKey(List<T> list, String removeKey){
        Gson g =  new Gson();
        for(T obj : list){
            JsonObject json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            json.remove(removeKey);
            this.arr.add(json);
        }
        return this.arr;
    }

    public JsonArray listIntoArrayWithoutKey(List<T> list, List<String> removeKeys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        Gson g =  new Gson();
        JsonObject json;
        for(T obj : list){
            json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            for(String removeKey : removeKeys){
                System.out.println("remove: " + removeKey);
                json.remove(removeKey);
            }
            this.arr.add(json);
        }
        return this.arr;
    }
//
//    public JsonIter remove(String key){
//        json.remove(key);
//        return this;
//    }
//
//    public JsonObject create(){
//        return new JsonObject(); //TODO
//    }
}
