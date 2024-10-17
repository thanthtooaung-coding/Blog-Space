package com.vinn.blogspace.post.user.controller;

import com.vinn.blogspace.post.user.dto.PostDto;
import com.vinn.blogspace.post.user.dto.PostCreateDto;
import com.vinn.blogspace.post.user.dto.PostUpdateDto;
import com.vinn.blogspace.post.user.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/posts/users")
public class PostController {

    private final PostService postService;
    private final RepresentationModelAssembler<PostDto, EntityModel<PostDto>> postModelAssembler;

    public PostController(PostService postService,
                          RepresentationModelAssembler<PostDto, EntityModel<PostDto>> postModelAssembler) {
        this.postService = postService;
        this.postModelAssembler = postModelAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PostDto>>> getAllPosts(Pageable pageable) {
        Page<PostDto> posts = postService.getAllPosts(pageable);
        PagedModel<EntityModel<PostDto>> pagedModel = PagedModel.of(
                posts.getContent().stream()
                        .map(postModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        posts.getSize(),
                        posts.getNumber(),
                        posts.getTotalElements(),
                        posts.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PostDto>> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        EntityModel<PostDto> resource = postModelAssembler.toModel(post)
                .add(linkTo(methodOn(PostController.class).getAllPosts(Pageable.unpaged())).withRel("posts"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<PostDto>> createPost(@Valid @RequestBody PostCreateDto postCreateDto) {
        PostDto createdPost = postService.createPost(postCreateDto);
        EntityModel<PostDto> resource = postModelAssembler.toModel(createdPost);
        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PostDto>> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDto postUpdateDto) {
        PostDto updatedPost = postService.updatePost(id, postUpdateDto);
        EntityModel<PostDto> resource = EntityModel.of(updatedPost,
                linkTo(methodOn(PostController.class).getPostById(id)).withSelfRel(),
                linkTo(methodOn(PostController.class).getAllPosts(Pageable.unpaged())).withRel("posts"));
        return ResponseEntity
                .ok()
                .location(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<PostDto>>> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) List<Long> tagIds,
            Pageable pageable) {
        Page<PostDto> posts = postService.searchPosts(title, categoryIds, tagIds, pageable);
        PagedModel<EntityModel<PostDto>> pagedModel = PagedModel.of(
                posts.getContent().stream()
                        .map(postModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        posts.getSize(),
                        posts.getNumber(),
                        posts.getTotalElements(),
                        posts.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EntityModel<PostDto>> updatePostStatus(@PathVariable Long id, @RequestParam String status) {
        PostDto updatedPost = postService.updatePostStatus(id, status);
        EntityModel<PostDto> resource = postModelAssembler.toModel(updatedPost);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<PagedModel<EntityModel<PostDto>>> getPostsByUser(
            @PathVariable String username,
            Pageable pageable) {
        Page<PostDto> posts = postService.getPostsByUser(username, pageable);
        PagedModel<EntityModel<PostDto>> pagedModel = PagedModel.of(
                posts.getContent().stream()
                        .map(postModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        posts.getSize(),
                        posts.getNumber(),
                        posts.getTotalElements(),
                        posts.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }
}