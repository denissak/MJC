package com.epam.esm.controller;

import com.epam.esm.AutoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
public class AutoGeneratorController {

    private final AutoGenerator autoGenerator;

    @Autowired
    public AutoGeneratorController(AutoGenerator autoGenerator) {
        this.autoGenerator = autoGenerator;
    }

    @PostMapping("/certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCertificate() {
        autoGenerator.createAutoCertificate();
    }

    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag() {
        autoGenerator.createAutoTag();
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser() {
        autoGenerator.createAutoUser();
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder() {
        autoGenerator.createAutoOrder();
    }
}
