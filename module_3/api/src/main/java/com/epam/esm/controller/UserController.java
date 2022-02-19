package com.epam.esm.controller;

import com.epam.esm.UserService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readById(@PathVariable long id) {
        return userService.readById(id);
    }

/*    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readByName(@PathVariable String login) {
        return userService.readByLogin(login);
    }*/

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> readsAllUsers() {
        return userService.readAll();
    }
}
