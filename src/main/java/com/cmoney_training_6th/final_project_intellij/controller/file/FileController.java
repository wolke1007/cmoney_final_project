package com.cmoney_training_6th.final_project_intellij.controller.file;

import com.cmoney_training_6th.final_project_intellij.service.FilesStorageService;
import com.cmoney_training_6th.final_project_intellij.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


@Controller
public class FileController {

    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/download/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@RequestHeader("Authorization") String jwt,
                                            @PathVariable String filename) {
        if(!userService.isExist(jwt)){
            return null;
        }
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}