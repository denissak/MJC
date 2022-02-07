package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfig {
    private static final String SOURSE = "classpath:messages";
    private static final String ENCODING = "UTF-8";

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(SOURSE);
        messageSource.setDefaultEncoding(ENCODING);
        return messageSource;
    }
}