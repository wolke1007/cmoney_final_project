package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecipe;
import com.cmoney_training_6th.final_project_intellij.model.MedicalTreatment;
import com.cmoney_training_6th.final_project_intellij.service.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
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
    public CommonResponse addMedicalItem() {
        return null;
    }

    @Override
    public CommonResponse editMedicalItem() {
        return null;
    }

    @Override
    public CommonResponse deleteMedicalItem() {
        return null;
    }

    @Override
    public CommonResponse addMedicalRecipe() {
        return null;
    }

    @Override
    public CommonResponse editMedicalRecipe() {
        return null;
    }

    @Override
    public CommonResponse deleteMedicalRecipe() {
        return null;
    }

    @Override
    public CommonResponse addMedicalTreatment() {
        return null;
    }

    @Override
    public CommonResponse editMedicalTreatment() {
        return null;
    }

    @Override
    public CommonResponse deleteMedicalTreatment() {
        return null;
    }

    @Override
    public CommonResponse addMedicalRecord() {
        return null;
    }

    @Override
    public CommonResponse editMedicalRecord() {
        return null;
    }

    @Override
    public CommonResponse deleteMedicalRecord() {
        return null;
    }
}
