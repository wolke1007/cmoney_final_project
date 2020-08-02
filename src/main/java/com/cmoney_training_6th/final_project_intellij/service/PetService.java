package com.cmoney_training_6th.final_project_intellij.service;

import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {
    CommonResponse add(Pet newPet);
    CommonResponse add(String jwt, Pet newPet);
    CommonResponse edit(Pet petEdit);
    CommonResponse edit(String jwt, Pet petEdit);
    CommonResponse delete(Pet petDelete);
    CommonResponse delete(String jwt, Pet petDelete);
    CommonResponse getAll();
    CommonResponse getByUserId(int userId);
    CommonResponse getByHospitalId(int hospitalId);
    CommonResponse getByToken(String jwt);
    CommonResponse uploadPetPhoto(String jwt, MultipartFile file, int petId);
    CommonResponse getPhotoByPetId(String jwt, int petId);
    CommonResponse getPhotosByToken(String jwt);
}
