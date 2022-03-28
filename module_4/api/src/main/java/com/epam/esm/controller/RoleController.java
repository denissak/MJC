package com.epam.esm.controller;

import com.epam.esm.RoleService;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with roles.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Read role with passed id.
     *
     * @param id id of role to be read
     * @return role with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto readById(@PathVariable long id) {
        try {
            Link link = linkTo(RoleController.class).withSelfRel();
            RoleDto roleDto = roleService.findById(id);
            roleDto.add(link);
            return roleDto;
        } catch (RuntimeException e){
            throw NotFoundException.notFoundWithRoleId(id).get();
        }
    }

    /**
     * Read all roles.
     *
     * @return all roles
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<RoleDto> readsAllRoles() {
        List<RoleDto> roleDtoList = roleService.findAll();
        return addLinksToRole(roleDtoList);
    }

    /**
     * Create and save the passed role.
     *
     * @param roleDto the order to be saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        try {
            return roleService.create(roleDto);
        } catch (RuntimeException e){
            throw DuplicateException.roleExists().get();
        }
    }

    private CollectionModel<RoleDto> addLinksToRole(List<RoleDto> roleDtoList) {
        for (final RoleDto roleDto : roleDtoList) {
            Link selfLink = linkTo(methodOn(RoleController.class)
                    .readById(roleDto.getId())).withSelfRel();
            roleDto.add(selfLink);
        }
        Link link = linkTo(RoleController.class).withSelfRel();
        return CollectionModel.of(roleDtoList, link);
    }
}
