package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfig  {

/*    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
*//*        return null;*//*
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
//        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
//        return new String[0];
    }*/


}
