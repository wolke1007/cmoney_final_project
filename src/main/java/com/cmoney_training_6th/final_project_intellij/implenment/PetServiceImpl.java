package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.controller.FileController;
import com.cmoney_training_6th.final_project_intellij.controller.user.PetController;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.service.FilesStorageService;
import com.cmoney_training_6th.final_project_intellij.service.PetService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PetPhotoRepository petPhotoRepository;
    @Autowired
    private FilesStorageService filesStorageService;

    @Override
    public CommonResponse add(Pet newPet) {
        try {
            petRepository.save(newPet);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        }
    }

    @Override
    public CommonResponse add(String jwt, Pet newPet) {
        try {
            String token = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            Optional<User> user = userRepository.findByUsername(username);
            newPet.setUserId(user.get().getId());
            petRepository.save(newPet);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (StringIndexOutOfBoundsException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
    }

    @Override
    public CommonResponse edit(Pet petEdit) {
        try {
            Pet pet = petRepository.findById(petEdit.getId()).orElse(null);
            if (pet == null) {
                return new CommonResponse("pet " + petEdit.getId() + " not found: ", 404);
            }
            pet.setUserId(petEdit.getUserId());
            pet.setChip(petEdit.getChip());
            pet.setWeight(petEdit.getWeight());
            pet.setGender(petEdit.getGender());
            pet.setNeutered(petEdit.isNeutered());
            pet.setAge(petEdit.getAge());
            pet.setBreed(petEdit.getBreed());
            pet.setSpecies(petEdit.getSpecies());
            pet.setName(petEdit.getName());
            pet.setAllergicWith(petEdit.getAllergicWith());
            pet.setOwnDate(petEdit.getOwnDate());
            petRepository.save(pet);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        } catch (NoSuchElementException e) {
            return new CommonResponse("id " + petEdit.getId() + " not found: " + e.getMessage(), 404);
        }
    }

    @Override
    public CommonResponse edit(String jwt, Pet petEdit) {
        try {
            String token = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User user = userRepository.findByUsername(username).orElse(null);
            Pet pet = petRepository.findByUserIdAndId(user.getId(), petEdit.getId()).orElse(null);
            if (pet == null) {
                return new CommonResponse("user_id:" + user.getId() + " + pet_id:" + petEdit.getId() + " not found.", 404);
            }
            return this.edit(pet);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        } catch (NoSuchElementException e) {
            return new CommonResponse("id " + petEdit.getId() + " not found: " + e.getMessage(), 404);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (StringIndexOutOfBoundsException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
    }

    @Override
    public CommonResponse delete(Pet petDelete) {
        try {
            Pet pet = petRepository.findById(petDelete.getId()).orElse(null);
            if (pet == null) {
                return new CommonResponse("pet " + petDelete.getId() + " not found", 404);
            }
            petRepository.delete(pet);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        } catch (NoSuchElementException e) {
            return new CommonResponse("id " + petDelete.getId() + " not found: " + e.getMessage(), 404);
        }
    }

    @Override
    public CommonResponse delete(String jwt, Pet petDelete) {
        try {
            String token = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User user = userRepository.findByUsername(username).orElse(null);
            Pet pet = petRepository.findByUserIdAndId(user.getId(), petDelete.getId()).orElse(null);
            if (pet == null) {
                return new CommonResponse("user_id:" + user.getId() + " + pet_id:" + petDelete.getId() + " not found.", 404);
            }
            petRepository.delete(pet);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        } catch (NoSuchElementException e) {
            return new CommonResponse("id " + petDelete.getId() + " not found: " + e.getMessage(), 404);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (StringIndexOutOfBoundsException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        }
    }

    @Override
    public CommonResponse getAll() {
        Pet p = new Pet();
        List<Pet> pets = petRepository.findAll();
        List<User> users = userRepository.findAll();
        JsonObject json = new JsonObject();
        Gson g = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create();
        JsonArray petArr = new JsonArray();
        for (Pet pet : pets) {
            JsonObject petJson = g.toJsonTree(pet).getAsJsonObject();
            int user_id = petJson.get("userId").getAsInt();
            User user = userRepository.findById(user_id).get();
            String username = user.getUsername();
            String phone = user.getPhone();
            String lastName = user.getLastName();
            String firstName = user.getFirstName();
            petJson.addProperty("ownerEmail", username);
            petJson.addProperty("ownerPhone", phone);
            petJson.addProperty("ownerName", lastName + firstName);
            petArr.add(petJson);
        }
        json.add("pet_list", petArr);
        return new CommonResponse(json, 200);
    }

    @Override
    public CommonResponse getByUserId(int userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(pets, "medicalRecord");
        return new CommonResponse(arr, 200);
    }

    @Override
    public CommonResponse getByToken(String jwt) {
        try {
            String token = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            Optional<User> user = userRepository.findByUsername(username);
            List<Pet> pets = petRepository.findByUserId(user.get().getId());
            JsonArray jsonArr;
            JsonIter ji = new JsonIter();
            jsonArr = ji.listIntoArray(pets);
            return new CommonResponse(jsonArr, 200);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (StringIndexOutOfBoundsException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
    }

    @Override
    public CommonResponse getByHospitalId(int hospitalId) {
        List<Pet> pets = petRepository.findByUserId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(pets, "medicalRecord");
        return new CommonResponse(arr, 200);
    }

    @Override
    public CommonResponse uploadPetPhoto(String jwt, MultipartFile file, int petId) {
        try {
            String a = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(a);
            User existingUser = userRepository.findByUsername(username).orElse(null);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
        if (file.isEmpty()) {
            return null;
        }
        String message = "";
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MMM/d/E_HH:mm:ss", Locale.ENGLISH);
            Date now = new Date();
            PetPhoto fileInfo = new PetPhoto();
            long starttime = System.currentTimeMillis();
            filesStorageService.save(file, starttime);
            fileInfo.setName(starttime + file.getOriginalFilename());
            fileInfo.setTime(starttime);
            fileInfo.setDate(date.format(now));
            fileInfo.setUrl("./uploads");
            fileInfo.setPetId(petId);
            fileRepository.save(fileInfo);
            message = "Uploaded the file successfully: " + file.getName();
            return new CommonResponse(message, 200);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new CommonResponse(message, 404);
        }
    }

    @Override
    public CommonResponse getPhotoByPetId(int petId) {
        try {
            JsonArray arr = new JsonArray();
            // 這邊沒有做是不是飼主的判斷，目前是所有人都可以拿指定 pet 的圖片(前端要求，方便前端測試)
            if (petRepository.findById(petId).orElse(null) == null) {
                return new CommonResponse("pet not found.", 404);
            }
            List<PetPhoto> petPhotos = petPhotoRepository.findAllByPetId(petId);
            if (petPhotos.size() == 0) {
                return new CommonResponse("pet photo not found.", 404);
            }
            for (PetPhoto petPhoto : petPhotos) {
                JsonObject json = new JsonObject();
                Resource r = filesStorageService.load(petPhoto.getName());
                json.addProperty("petId", petId);
                UriComponentsBuilder uriComponentsBuilder = MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFile",
                                r.getFile().toPath().getFileName().toString());
                if(uriComponentsBuilder == null){
                    continue;
                }
                json.addProperty("URL", uriComponentsBuilder.build().toString());
                arr.add(json);
            }
            return new CommonResponse(arr, 200);
        } catch (IOException e) {
            return new CommonResponse("no such file", 404);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
    }

    @Override
    public CommonResponse getPhotosByToken(String jwt) {
        try {
            String a = jwt.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(a);
            User user = userRepository.findByUsername(username).orElse(null);
            JsonArray arr = new JsonArray();
            List<PetPhoto> petPhotos = petPhotoRepository.findPetPhotosByUserId(user.getId());
            System.out.println("DEBUG petphotos count: " + petPhotos.size());
            if (petPhotos.size() == 0) {
                return new CommonResponse("no pet photo found.", 404);
            }
            for (PetPhoto petPhoto : petPhotos) {
                JsonObject json = new JsonObject();
                Resource r = filesStorageService.load(petPhoto.getName());
                json.addProperty("petId", petPhoto.getPetId());
                UriComponentsBuilder uriComponentsBuilder = MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFile",
                                r.getFile().toPath().getFileName().toString());
                if(uriComponentsBuilder == null){
                    continue;
                }
                json.addProperty("URL", uriComponentsBuilder.build().toString());
                arr.add(json);
            }
            return new CommonResponse(arr, 200);
        } catch (IOException e) {
            return new CommonResponse("no such file", 404);
        } catch (ExpiredJwtException e) {
            return new CommonResponse("token expired: " + e.getMessage(), 403);
        }
    }

}
