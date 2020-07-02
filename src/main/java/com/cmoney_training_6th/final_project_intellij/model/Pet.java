package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @Column(columnDefinition="nvarchar(255)")
    private String name;

    private int age;

    private int weight;

    private int gender;

    @Column(columnDefinition="nvarchar(255)")
    private String breed;

    @Column(columnDefinition="nvarchar(255)")
    private String species;

    private String chip;

    @Column(columnDefinition="nvarchar(255)")
    private String allergic_with;

    private boolean neutered;

    @Column(length=50)
    private String own_date;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getAllergic_with() {
        return allergic_with;
    }

    public void setAllergic_with(String allergic_with) {
        this.allergic_with = allergic_with;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public String getOwn_date() {
        return own_date;
    }

    public void setOwn_date(String own_date) {
        this.own_date = own_date;
    }
}