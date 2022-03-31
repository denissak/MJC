package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class ApplicationRunner extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
