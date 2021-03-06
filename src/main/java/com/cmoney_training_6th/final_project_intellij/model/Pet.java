package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="user_id")
    private int userId; //FK

    @Column(columnDefinition="nvarchar(255)")
    private String name = "";

    private int age = 0;

    private int weight = 0;

    private int gender = 0;

    @Column(columnDefinition="nvarchar(255)")
    private String breed = "";

    @Column(columnDefinition="nvarchar(255)")
    private String species = "";

    private String chip = "";

    @Column(name="allergic_with", columnDefinition="nvarchar(255)")
    private String allergicWith = "";

    private boolean neutered = false;

    @Column(name="own_date", length=50)
    private String ownDate = "";

//    @OneToOne(mappedBy = "pet")
//    private MedicalRecord medicalRecord;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="pet_id", referencedColumnName = "id")
    List<Reservation> reservation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="pet_id", referencedColumnName = "id")
    List<MedicalRecord> medicalRecord;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="pet_id", referencedColumnName = "id")
    List<PetPhoto> petPhotos = new ArrayList<>();

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public List<MedicalRecord> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<MedicalRecord> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAllergicWith() {
        return allergicWith;
    }

    public void setAllergicWith(String allergicWith) {
        this.allergicWith = allergicWith;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public String getOwnDate() {
        return ownDate;
    }

    public void setOwnDate(String ownDate) {
        this.ownDate = ownDate;
    }

    public List<PetPhoto> getPetPhotos() {
        return petPhotos;
    }

    public void setPetPhotos(List<PetPhoto> petPhotos) {
        this.petPhotos = petPhotos;
    }
}