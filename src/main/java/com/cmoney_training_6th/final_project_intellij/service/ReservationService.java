package com.cmoney_training_6th.final_project_intellij.service;

public interface ReservationService {
    public abstract void add(int hospitalId);
    public abstract void edit(int doctorId);
    public abstract void delete(int doctorId);
}
