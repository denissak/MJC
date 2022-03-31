package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class ApplicationRunner extends SpringServletContainerInitializer {

    //    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ServletInitializer.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

}
