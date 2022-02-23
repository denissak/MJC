package com.epam.esm.controller;

import com.epam.esm.UserService;
import com.epam.esm.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readById(@PathVariable long id) {
        Link link = linkTo(UserController.class).withSelfRel();
        UserDto userDto = userService.readById(id);
        userDto.add(link);
        return userDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> readsAllUsers() {
        List<UserDto> userDtoList = userService.readAll();
        return addLinksToUser(userDtoList);
    }

    private CollectionModel<UserDto> addLinksToUser(List<UserDto> userDtoList) {
        for (final UserDto userDto : userDtoList) {
            Link selfLink = linkTo(methodOn(UserController.class)
                    .readById(userDto.getId())).withSelfRel();
            userDto.add(selfLink);
        }
        Link link = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(userDtoList, link);
    }
}
