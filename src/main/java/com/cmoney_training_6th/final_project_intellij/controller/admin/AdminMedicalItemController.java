package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.Doctor;
import com.cmoney_training_6th.final_project_intellij.model.MedicalItem;
import com.cmoney_training_6th.final_project_intellij.model.MedicalRecord;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/medical_item")
public class AdminMedicalItemController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MedicalItemRepository medicalItemRepository;

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewMedcalItem(
            HttpServletResponse response,
            @RequestBody MedicalItem request
    ) {
        try {
            medicalItemRepository.save(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editMedcalItem(
            HttpServletResponse response,
            @RequestBody MedicalItem request
    ) {
        try {
            MedicalItem medicalItem = medicalItemRepository.findByName(request.getName()).orElse(null); // 確認 name 是否可以找到東西
            if(medicalItem == null){
                response.setStatus(404);
                return new CommonResponse("medical item " + request.getId() + " not found: ", 404).toString();
            }
            medicalItem.setDescription(request.getDescription());
            medicalItem.setItemType(request.getItemType());
            medicalItem.setName(request.getName());
            medicalItemRepository.save(medicalItem);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String deleteMedcalItem(
            HttpServletResponse response,
            @RequestBody MedicalItem request
    ) {
        try {
            medicalItemRepository.delete(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public String getAllMedcalItems() {
        JsonIter ji = new JsonIter();
        JsonArray jsonArr = ji.listIntoArrayWithoutKey(medicalItemRepository.findAll(), "recipes");
        return new CommonResponse(jsonArr, 200).toString();
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE) // debug 用
    public String getPageMedcalItems(HttpServletResponse response,
                                     @RequestParam int page,
                                     @RequestParam int size) {
        Page<MedicalItem> pageResult = medicalItemRepository.findAll(
                PageRequest.of(page,  // 查詢的頁數，從0起算
                        size, // 查詢的每頁筆數
                        Sort.by("itemType").descending())); // 依itemType欄位降冪排序
        List<MedicalItem> medicalItems =  pageResult.getContent();
        JsonIter ji = new JsonIter();
        JsonArray jsonArr = ji.listIntoArrayWithoutKey(medicalItems, "recipes");
        return new CommonResponse(jsonArr, 200).toString();
    }

}


