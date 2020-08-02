package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecipe;
import com.cmoney_training_6th.final_project_intellij.model.MedicalTreatment;
import com.cmoney_training_6th.final_project_intellij.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalServiceImpl implements MedicalService {
    /*
       這邊包含了醫療用品、藥單、醫療紀錄、病歷的所有方法
       想法是 MedicalService 可以提供所有 Medical 命名的相關服務
       所以使用時可以從這邊找到所有 Medical 相關的操作
    */

    @Autowired
    private MedicalItemRepository medicalItemRepository;
    @Autowired
    private MedicalRecipeRepository medicalRecipeRepository;
    @Autowired
    private MedicalTreatmentRepository medicalTreatmentRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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
