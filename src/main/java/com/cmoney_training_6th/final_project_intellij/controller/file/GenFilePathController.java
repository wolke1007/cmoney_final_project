package com.cmoney_training_6th.final_project_intellij.controller.file;

import com.cmoney_training_6th.final_project_intellij.service.FilesStorageService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

public class GenFilePathController {

    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/download/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> genFilePath(@PathVariable String filename) {
        System.out.println("enter gen file path");
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}