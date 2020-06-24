package com.cmoney_training_6th.final_project_intellij;

import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FinalProjectIntellijApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectIntellijApplication.class, args);
    }

}