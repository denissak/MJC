package com.epam.esm;

import org.springframework.stereotype.Component;

public interface AutoGenerator {

    void createAutoCertificate();

    void createAutoOrder();

    void createAutoTag();

    void createAutoUser();
}
