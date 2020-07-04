package com.cmoney_training_6th.final_project_intellij.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CommonResponse<T> extends JsonElement{

    private T message;
    private int status;

    public CommonResponse(T message, int status){
        this.message = message;
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public JsonElement deepCopy() {
        return null;
    }
}
