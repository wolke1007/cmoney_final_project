package com.cmoney_training_6th.final_project_intellij.model;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="hospital_id")
    private Hospital hospital;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="pet_id")
    private Pet pet;

//    @Column(nullable=false, length=50)
    @Column(length=50)
    private String create_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}