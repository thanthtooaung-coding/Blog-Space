package com.vinn.blogspace.tag.assemblers;

import com.vinn.blogspace.common.assemblers.BaseModelAssembler;
import com.vinn.blogspace.tag.controller.TagController;
import com.vinn.blogspace.tag.dto.TagDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler extends BaseModelAssembler<TagDto> {

    @Override
    protected EntityModel<TagDto> addLinks(EntityModel<TagDto> model, TagDto tag) {
        model.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
        model.add(linkTo(methodOn(TagController.class).getAllTags(null)).withRel("tags"));
        return model;
    }

    @Override
    @NonNull
    public EntityModel<TagDto> toModel(TagDto tag) {
        EntityModel<TagDto> model = super.toModel(tag);
        return addLinks(model, tag);
    }
}
