package com.cmoney_training_6th.final_project_intellij.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    //加time在檔名裡
    public void save(MultipartFile file, long time);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
