package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="owner_user_id")
    private User user;

//    @Column(nullable=false, unique=true)
    @Column(unique=true)
    private int uni_serial_id;

//    @Column(nullable=false, columnDefinition="nvarchar(255)")
    @Column(columnDefinition="nvarchar(255)")
    private String name;

    private String phone;

    @Column(columnDefinition="nvarchar(255)")
    private String address_city;

    @Column(columnDefinition="nvarchar(255)")
    private String address_area;

    @Column(columnDefinition="nvarchar(255)")
    private String address_line;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUni_serial_id() {
        return uni_serial_id;
    }

    public void setUni_serial_id(int uni_serial_id) {
        this.uni_serial_id = uni_serial_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_area() {
        return address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }
}