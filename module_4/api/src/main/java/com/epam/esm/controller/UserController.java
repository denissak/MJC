package com.epam.esm.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.UserService;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for working with users.
 */
@RestController("/rest")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Read user with passed id.
     *
     * @param id id of tag to be read
     * @return tag with passed id
     */
    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readById(@PathVariable long id) {
        try {
            Link link = linkTo(UserController.class).withSelfRel();
            UserDto userDto = userService.readById(id);
            userDto.add(link);
            return userDto;
        } catch (RuntimeException e){
            throw NotFoundException.notFoundWithUserId(id).get();
        }
    }

    /**
     * Refresh token.
     */
    @GetMapping("/token/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                UserDto userDto = userService.readByName(username);
                String accessToken = JWT.create()
                        .withSubject(userDto.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 *60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", userDto.getRoleDto().getName())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw RefreshTokenException.refreshTokenFailed().get();
        }
    }

    /**
     * Read all users.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all users
     */
    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> readsAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<UserDto> userDtoList = userService.readAll(page, size);
        return addLinksToUser(userDtoList);
    }

    /**
     * Registration new.
     *
     * @param userDto the user that try registration
     */

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(/*@RequestBody*/ UserDto userDto) {
        try {
            return userService.create(userDto);
        } catch (RuntimeException e){
            throw DuplicateException.userExists().get();
        }
    }

    /**
     * Delete user with passed id.
     *
     * @param id the id of user to be deleted
     */
    @DeleteMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        try {
            userService.delete(id);
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithTagId(id).get();
        }
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
