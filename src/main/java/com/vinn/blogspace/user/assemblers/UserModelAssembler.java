package com.vinn.blogspace.user.assemblers;

import com.vinn.blogspace.common.assemblers.BaseModelAssembler;
import com.vinn.blogspace.user.controller.UserController;
import com.vinn.blogspace.user.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler extends BaseModelAssembler<UserDto> {

    @Override
    protected EntityModel<UserDto> addLinks(EntityModel<UserDto> model, UserDto user) {
        model.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers(null)).withRel("users"));
        return model;
    }

    @Override
    @NonNull
    public EntityModel<UserDto> toModel(@NonNull UserDto user) {
        EntityModel<UserDto> model = super.toModel(user);
        return addLinks(model, user);
    }
}
