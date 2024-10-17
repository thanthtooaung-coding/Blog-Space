package com.vinn.blogspace.category.assemblers;

import com.vinn.blogspace.category.controller.CategoryController;
import com.vinn.blogspace.category.dto.CategoryDto;
import com.vinn.blogspace.common.assemblers.BaseModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler extends BaseModelAssembler<CategoryDto> {

    @Override
    protected EntityModel<CategoryDto> addLinks(EntityModel<CategoryDto> model, CategoryDto category) {
        model.add(linkTo(methodOn(CategoryController.class).getCategoryById(category.getId())).withSelfRel());
        model.add(linkTo(methodOn(CategoryController.class).getAllCategories(null)).withRel("categories"));
        return model;
    }

    @Override
    @NonNull
    public EntityModel<CategoryDto> toModel(CategoryDto category) {
        EntityModel<CategoryDto> model = super.toModel(category);
        return addLinks(model, category);
    }
}
