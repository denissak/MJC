package com.epam.esm.controller;

import com.epam.esm.AutoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for database auto-generation
 */
@RestController
@RequestMapping("/auto")
public class AutoGeneratorController {

    private final AutoGenerator autoGenerator;

    @Autowired
    public AutoGeneratorController(AutoGenerator autoGenerator) {
        this.autoGenerator = autoGenerator;
    }

    /**
     * Auto-create Certificates in DataBase
     */
    @PostMapping("/certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCertificate() {
        autoGenerator.createAutoCertificate();
    }

    /**
     * Auto-create Tags in DataBase
     */
    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag() {
        autoGenerator.createAutoTag();
    }

    /**
     * Auto-create Users in DataBase
     */
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser() {
        autoGenerator.createAutoUser();
    }

    /**
     * Auto-create Orders in DataBase
     */
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder() {
        autoGenerator.createAutoOrder();
    }
}
