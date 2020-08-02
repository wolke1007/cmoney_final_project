package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.*;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class JsonIter <T>{

    private JsonArray arr;
    private JsonObject json;
    private T obj;
    private Gson g;

    public JsonIter(){
        // 用 Modifier 可以去除指定的屬性格式不序列化，可避免如果有實體關聯上有迴圈而造成 stack overflow
        this.g = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create();
    }

    public JsonArray listIntoArray(List<T> list){
        JsonArray arr = new JsonArray();
        for(T obj : list){
            arr.add((JsonObject) g.toJsonTree(obj).getAsJsonObject());
        }
        return arr;
    }

    public JsonArray listIntoArrayWithoutKey(List<T> list, String removeKey){
        JsonArray arr = new JsonArray();
        for(T obj : list){
            JsonObject json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            json.remove(removeKey);
            arr.add(json);
        }
        return arr;
    }

    public JsonArray listIntoArrayWithoutKeys(List<T> list, List<String> removeKeys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonArray arr = new JsonArray();
        JsonObject json;
        for(T obj : list){
            json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            for(String removeKey : removeKeys){
                json.remove(removeKey);
            }
            arr.add(json);
        }
        return arr;
    }

    public JsonObject objIntoJsonWithoutKeys(T obj, List<String> removeKeys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonObject json;
        json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
        for(String removeKey : removeKeys){
            json.remove(removeKey);
        }
        return json;
    }

    public JsonObject jsonConcact(JsonObject jsonA, JsonObject jsonB){ // json 相加
        JsonObject json = new JsonObject();
        for(String key : jsonB.keySet()){
            json.add(key, jsonB.get(key));
        }
        for(String key : jsonA.keySet()){
            json.add(key, jsonA.get(key));
        }
        return json;
    }

    public JsonArray listIntoArrayWithKeys(List<T> list, List<String> keys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonArray arr = new JsonArray();
        JsonObject json;
        for(T obj : list){
            json = (JsonObject) g.toJsonTree(obj).getAsJsonObject();
            JsonObject newJson = new JsonObject();
            for(String key : keys){
                newJson.add(key, json.get(key));
            }
            arr.add(newJson);
        }
        return arr;
    }

    public JsonArray listIntoArrayWithKeys(JsonArray list, List<String> keys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonArray arr = new JsonArray();
        if(list == null){
            System.out.println("list is null");
            return arr;
        }
        JsonObject json;
        for(JsonElement je : list){
            JsonObject newJson = new JsonObject();
            for(String key : keys){
                newJson.add(key, je.getAsJsonObject().get(key));
            }
            arr.add(newJson);
        }
        return arr;
    }

    public JsonObject objIntoJsonWithKeys(T obj, List<String> keys){ // 可這樣的方式代入 Arrays.asList("roasters", "skill")
        JsonObject json = new JsonObject();
        JsonObject obJjson = g.toJsonTree(obj).getAsJsonObject();
        for(String key : keys){
            json.add(key, obJjson.get(key));
        }
        return json;
    }

    public JsonArray listIntoArray(List<T> list, Callable<T> func) throws Exception {
        JsonArray arr = new JsonArray();
        for(T obj : list){
            arr.add((JsonObject) g.toJsonTree(obj).getAsJsonObject());
            JsonObject t = (JsonObject)func.call();
            System.out.println(t.get("test"));
        }
        return arr;
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
