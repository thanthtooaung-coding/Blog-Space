package com.vinn.blogspace.post.user.assemblers;

import com.vinn.blogspace.common.assemblers.BaseModelAssembler;
import com.vinn.blogspace.post.user.controller.PostController;
import com.vinn.blogspace.post.user.dto.PostDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PostModelAssembler extends BaseModelAssembler<PostDto> {

    @Override
    protected EntityModel<PostDto> addLinks(EntityModel<PostDto> model, PostDto post) {
        model.add(linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel());
        model.add(linkTo(methodOn(PostController.class).getAllPosts(null)).withRel("posts"));
        return model;
    }

    @Override
    @NonNull
    public EntityModel<PostDto> toModel(@NonNull PostDto post) {
        EntityModel<PostDto> model = super.toModel(post);
        return addLinks(model, post);
    }
}
