package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeWrapper {

    public DateTimeWrapper() {
    }

    public LocalDateTime wrapDateTime() {
        return LocalDateTime.now();
    }
}
