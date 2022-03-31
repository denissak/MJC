package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class ApplicationRunner extends /*SpringServletContainerInitializer*/SpringBootServletInitializer{

        @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationRunner.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
