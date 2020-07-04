package com.cmoney_training_6th.final_project_intellij.util;

public class ValidateParameter {

    private boolean result;
    private String name;
    private String str;
    private String resultMsg;

    public ValidateParameter(String name, String str){
        this.result = true;
        this.name = name;
        this.str = str;
        this.resultMsg = "";
    }

    public ValidateParameter strLongerThan(int max){
        if(this.str.length() > max){
            this.result = false;
            System.out.println("max:" + max); // DEBUG
            System.out.println("this.str.length():" + this.str.length()); // DEBUG
            this.resultMsg+=" longer than " + String.valueOf(max);
        }
        return this;
    }

    public ValidateParameter strShorterThan(int min){
        if(this.str.length() < min){
            this.result = false;
            this.resultMsg+=" shorter than " + String.valueOf(min);
        }
        return this;
    }

    public boolean getResult(){
        return this.result;
    }

    @Override
    public String toString(){
        return this.name + this.resultMsg;
    }

}
