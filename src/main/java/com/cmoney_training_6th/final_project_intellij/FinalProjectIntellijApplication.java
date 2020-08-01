package com.cmoney_training_6th.final_project_intellij;

import com.cmoney_training_6th.final_project_intellij.dao.UserRepository;
import com.cmoney_training_6th.final_project_intellij.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import java.util.TimeZone;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FinalProjectIntellijApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;


    public static void main(String[] args) {
        init();
        SpringApplication.run(FinalProjectIntellijApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }

    public static void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00")); // 讓 log 的時間可以符合 +8 時區
    }
}