package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.*;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.Callable;

public class JsonIter <T>{

    private JsonArray arr;
    private JsonObject json;
    private T obj;
    private Gson g;

    public JsonIter(){
        this.arr = new JsonArray();
        // 用 Modifier 可以去除指定的屬性格式不序列化，可避免造成 stack overflow
        this.g = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create();
    }
//
//    public JsonIter(JsonObject json, T obj){
//        this.json = json;
//        this.obj = obj;
//        this.arr = new JsonArray();
//    }

    public JsonArray listIntoArray(List<T> list){
        for(T obj : list){
            this.arr.add((JsonObject) g.toJsonTree(obj).getAsJsonObject());
        }
        return this.arr;
    }

    public JsonArray listIntoArrayWithoutKey(List<T> list, String removeKey){
        for(T obj : list){
            JsonObject json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            json.remove(removeKey);
            this.arr.add(json);
        }
        return this.arr;
    }

    public JsonArray listIntoArrayWithoutKeys(List<T> list, List<String> removeKeys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
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

    public JsonArray listIntoArrayWithKeys(List<T> list, List<String> keys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonObject json;
        for(T obj : list){
            json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            JsonObject newJson = new JsonObject();
            for(String key : keys){
                newJson.add(key, json.get(key));
            }
            this.arr.add(newJson);
        }
        return this.arr;
    }

    public JsonArray listIntoArrayWithKeys(JsonArray list, List<String> keys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        if(list == null){
            System.out.println("list is null");
            return null;
        }
        JsonObject json;
        for(JsonElement je : list){
            JsonObject newJson = new JsonObject();
            for(String key : keys){
                newJson.add(key, je.getAsJsonObject().get(key));
            }
            this.arr.add(newJson);
        }
        return this.arr;
    }

    public JsonArray listIntoArray(List<T> list, Callable<T> func) throws Exception {
        for(T obj : list){
            this.arr.add((JsonObject) g.toJsonTree(obj).getAsJsonObject());
            JsonObject t = (JsonObject)func.call();
            System.out.println(t.get("test"));
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
