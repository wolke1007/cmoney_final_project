package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int account_id;

    private String social_license_id;

    private String username;

    @Column(nullable=false, length=50)
    private String first_name;

    @Column(nullable=false, length=50)
    private String last_name;

    private String email;

    private String password;

    private boolean active;

    @Column(length=50)
    private String roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return account_id;
    }

    public void setId(Integer id) {
        this.account_id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}