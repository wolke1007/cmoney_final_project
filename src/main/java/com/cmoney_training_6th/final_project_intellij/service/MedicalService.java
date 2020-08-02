package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;

public interface MedicalService {
    CommonResponse addMedicalItem();
    CommonResponse editMedicalItem();
    CommonResponse deleteMedicalItem();
    CommonResponse addMedicalRecipe();
    CommonResponse editMedicalRecipe();
    CommonResponse deleteMedicalRecipe();
    CommonResponse addMedicalTreatment();
    CommonResponse editMedicalTreatment();
    CommonResponse deleteMedicalTreatment();
    CommonResponse addMedicalRecord();
    CommonResponse editMedicalRecord();
    CommonResponse deleteMedicalRecord();
}
