package com.cmoney_training_6th.final_project_intellij.util;

public class ErrorResponse<T> {

    private T message;
    private int status;

    public ErrorResponse(T message, int status){
        this.message = message;
        this.status = status;
    }

}
