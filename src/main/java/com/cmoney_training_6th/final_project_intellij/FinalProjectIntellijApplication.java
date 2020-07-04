package com.cmoney_training_6th.final_project_intellij;

import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FinalProjectIntellijApplication {

    public static void main(String[] args) { SpringApplication.run(FinalProjectIntellijApplication.class, args);
    }

}