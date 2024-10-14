package com.vinn.blogspace.common.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public abstract class BaseModelAssembler<T> implements RepresentationModelAssembler<T, EntityModel<T>> {

    @Override
    @NonNull
    public EntityModel<T> toModel(@NonNull T entity) {
        return EntityModel.of(entity);
    }

    protected abstract EntityModel<T> addLinks(EntityModel<T> model, T entity);
}
