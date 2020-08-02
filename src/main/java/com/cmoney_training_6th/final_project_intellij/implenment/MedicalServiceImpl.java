package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.dao.MedicalItemRepository;
import com.cmoney_training_6th.final_project_intellij.dao.MedicalRecipeRepository;
import com.cmoney_training_6th.final_project_intellij.dao.MedicalTreatmentRepository;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecipe;
import com.cmoney_training_6th.final_project_intellij.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalServiceImpl implements MedicalItemService,
        MedicalRecipeService, MedicalTreatmentService, MedicalRecordService {

    @Autowired
    private MedicalItemRepository medicalItemRepository;

    @Autowired
    private MedicalRecipeRepository medicalRecipeRepository;

    @Override
    public void addMedicalItem() {

    }

    @Override
    public void editMedicalItem() {

    }

    @Override
    public void deleteMedicalItem() {

    }

    @Override
    public void addMedicalRecipe() {

    }

    @Override
    public void editMedicalRecipe() {

    }

    @Override
    public void deleteMedicalRecipe() {

    }

    @Override
    public void addMedicalRecord() {

    }

    @Override
    public void editMedicalRecord() {

    }

    @Override
    public void deleteMedicalRecord() {

    }

    @Override
    public void addMedicalTreatment() {

    }

    @Override
    public void editMedicalTreatment() {

    }

    @Override
    public void deleteMedicalTreatment() {

    }
}
