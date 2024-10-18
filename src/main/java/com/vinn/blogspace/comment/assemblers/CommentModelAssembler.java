package com.vinn.blogspace.comment.assemblers;

import com.vinn.blogspace.common.assemblers.BaseModelAssembler;
import com.vinn.blogspace.comment.controller.CommentController;
import com.vinn.blogspace.comment.dto.CommentDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler extends BaseModelAssembler<CommentDto> {

    @Override
    protected EntityModel<CommentDto> addLinks(EntityModel<CommentDto> model, CommentDto comment) {
        model.add(linkTo(methodOn(CommentController.class).getCommentById(comment.getId())).withSelfRel());
        model.add(linkTo(methodOn(CommentController.class).getAllComments(null)).withRel("comments"));
        return model;
    }

    @Override
    @NonNull
    public EntityModel<CommentDto> toModel(CommentDto comment) {
        EntityModel<CommentDto> model = super.toModel(comment);
        return addLinks(model, comment);
    }
}
