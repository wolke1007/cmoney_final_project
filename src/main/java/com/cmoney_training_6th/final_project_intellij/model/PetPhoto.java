package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "pet_photo")
public class PetPhoto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="pet_id")
    private Pet pet;

    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}