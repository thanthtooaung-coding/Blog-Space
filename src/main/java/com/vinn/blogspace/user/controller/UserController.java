package com.vinn.blogspace.user.controller;

import com.vinn.blogspace.user.dto.UserCreateDto;
import com.vinn.blogspace.user.dto.UserDto;
import com.vinn.blogspace.user.dto.UserUpdateDto;
import com.vinn.blogspace.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RepresentationModelAssembler<UserDto, EntityModel<UserDto>> userModelAssembler;

    public UserController(
            UserService userService,
            RepresentationModelAssembler<UserDto, EntityModel<UserDto>> userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDto>>> getAllUsers(Pageable pageable) {
        Page<UserDto> users = userService.getAllUsers(pageable);
        PagedModel<EntityModel<UserDto>> pagedModel = PagedModel.of(
                users.getContent().stream()
                        .map(userModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        users.getSize(),
                        users.getNumber(),
                        users.getTotalElements(),
                        users.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        EntityModel<UserDto> resource = userModelAssembler.toModel(user)
                .add(linkTo(methodOn(UserController.class).getAllUsers(Pageable.unpaged())).withRel("users"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserDto>> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto createdUser = userService.createUser(userCreateDto);
        EntityModel<UserDto> resource = userModelAssembler.toModel(createdUser);
        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserDto updatedUser = userService.updateUser(id, userUpdateDto);
        EntityModel<UserDto> resource = userModelAssembler.toModel(updatedUser);
        return ResponseEntity
                .ok()
                .location(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }
}
