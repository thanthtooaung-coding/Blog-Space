package com.vinn.blogspace.comment.controller;

import com.vinn.blogspace.comment.dto.CommentCreateDto;
import com.vinn.blogspace.comment.dto.CommentDto;
import com.vinn.blogspace.comment.dto.CommentUpdateDto;
import com.vinn.blogspace.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final RepresentationModelAssembler<CommentDto, EntityModel<CommentDto>> commentModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CommentDto>>> getAllComments(Pageable pageable) {
        Page<CommentDto> comments = commentService.getAllComments(pageable);
        PagedModel<EntityModel<CommentDto>> pagedModel = PagedModel.of(
                comments.getContent().stream()
                        .map(commentModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        comments.getSize(),
                        comments.getNumber(),
                        comments.getTotalElements(),
                        comments.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CommentDto>> getCommentById(@PathVariable Long id) {
        CommentDto comment = commentService.getCommentById(id);
        EntityModel<CommentDto> resource = commentModelAssembler.toModel(comment)
                .add(linkTo(methodOn(CommentController.class).getAllComments(Pageable.unpaged())).withRel("comments"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CommentDto>> addComment(@Valid @RequestBody CommentCreateDto commentDto) {
        CommentDto createdComment = commentService.createComment(commentDto);
        EntityModel<CommentDto> resource = commentModelAssembler.toModel(createdComment);
        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CommentDto>> updateComment(@PathVariable Long id, @Valid @RequestBody CommentUpdateDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(id, commentDto);
        EntityModel<CommentDto> resource = commentModelAssembler.toModel(updatedComment);
        return ResponseEntity
                .ok()
                .location(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
