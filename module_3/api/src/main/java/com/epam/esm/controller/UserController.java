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
    public ResponseEntity<UserDto> readUserById(@PathVariable long id) {
        UserDto userDto = userService.readById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> readUserByName(@PathVariable String login) {
        UserDto userDto = userService.readByLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> readsAllUsers() {
        return userService.readAll();
    }
}
