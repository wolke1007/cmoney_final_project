package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.PetPhoto;
import com.cmoney_training_6th.final_project_intellij.model.Pet;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.service.FilesStorageService;
import com.cmoney_training_6th.final_project_intellij.service.PetService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/pet")
public class PetController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetPhotoRepository petPhotoRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPetByToken(HttpServletResponse response,
                                @RequestHeader("Authorization") String jwt) {
        CommonResponse ret = petService.getByToken(jwt);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String addNewPetByToken(
            HttpServletResponse response,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.add(jwt, request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String editPetByToken(
            HttpServletResponse response,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.edit(jwt, request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String deletePet(
            HttpServletResponse response,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Pet request
    ) {
        CommonResponse ret = petService.delete(jwt, request);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "照片上傳成功")})
    @ApiOperation("上傳寵物照片")
    @PostMapping(path = "/upload/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadPetPhoto(HttpServletResponse response,
                                 @RequestHeader("Authorization") String jwt,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("petId") int petId) {
        CommonResponse ret = petService.uploadPetPhoto(jwt, file, petId);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "{petId}/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhotoByPetId(HttpServletResponse response,
                                  @RequestHeader("Authorization") String jwt,
                                  @PathVariable("petId") int petId) throws IOException {
        CommonResponse ret = petService.getPhotoByPetId(jwt, petId);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

    @GetMapping(path = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPhotosByToken(HttpServletResponse response,
                                   @RequestHeader("Authorization") String jwt) throws IOException {
        CommonResponse ret = petService.getPhotosByToken(jwt);
        response.setStatus(ret.getStatus());
        return ret.toString();
    }

//    @GetMapping(path = "/getfiles", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getListFiles(HttpServletResponse response) {
//        Iterable<PetPhoto> petPhotos = petPhotoRepository.findAll();
//        List<PetPhoto> fileInfos = filesStorageService.loadAll().map(path -> {
//            String filename = path.getFileName().toString();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(PetController.class, "getFile", path.getFileName().toString()).build().toString();
//            int petId = petPhotos.iterator().next().getPetId();
//            System.out.println("pet petId:" + petId);
//            return new PetPhoto(filename, url, petId);
//        }).collect(Collectors.toList());
//        JsonIter ji = new JsonIter();
//        JsonArray arr = ji.listIntoArray(fileInfos);
//        return new CommonResponse(arr, 200).toString();
//    }

//    @GetMapping(value = "/download/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//
//        Resource file = filesStorageService.load(filename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

}