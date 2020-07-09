package com.cmoney_training_6th.final_project_intellij;

import java.io.Serializable;

public class AuthenticationAdminRequest implements Serializable {


    private String username;
    private String password;
    private int uniSerialId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUniSerialId() {
        return uniSerialId;
    }

    public void setUniSerialId(int uniSerialId) {
        this.uniSerialId = uniSerialId;
    }

    //need default constructor for JSON Parsing
    public AuthenticationAdminRequest()
    {

    }

    public AuthenticationAdminRequest(String username, String password, int uniSerialId) {
        this.setUsername(username);
        this.setPassword(password);
        this.setUniSerialId(uniSerialId);
    }
}
