package com.epam.esm.controller;

import com.epam.esm.RoleService;
import com.epam.esm.UserService;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Reads role with passed id.
     *
     * @param id id of role to be read
     * @return role with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto readById(@PathVariable long id) {
        Link link = linkTo(RoleController.class).withSelfRel();
        RoleDto roleDto = roleService.findById(id);
        roleDto.add(link);
        return roleDto;
    }

    /**
     * Read all users.
     *
     * @return all users
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<RoleDto> readsAllRoles(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<RoleDto> roleDtoList = roleService.findAll();
        return addLinksToRole(roleDtoList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        return roleService.create(roleDto);
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
