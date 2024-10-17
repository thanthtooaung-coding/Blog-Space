package com.vinn.blogspace.tag.controller;

import com.vinn.blogspace.tag.dto.TagDto;
import com.vinn.blogspace.tag.service.TagService;
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
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    private final RepresentationModelAssembler<TagDto, EntityModel<TagDto>> tagModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<TagDto>>> getAllTags(Pageable pageable) {
        Page<TagDto> tags = tagService.getAllTags(pageable);
        PagedModel<EntityModel<TagDto>> pagedModel = PagedModel.of(
                tags.getContent().stream()
                        .map(tagModelAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        tags.getSize(),
                        tags.getNumber(),
                        tags.getTotalElements(),
                        tags.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TagDto>> getTagById(@PathVariable Long id) {
        TagDto tag = tagService.getTagById(id);
        EntityModel<TagDto> resource = tagModelAssembler.toModel(tag)
                .add(linkTo(methodOn(TagController.class).getAllTags(Pageable.unpaged())).withRel("tags"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<TagDto>> addTag(@Valid @RequestBody TagDto tagDto) {
        TagDto createdTag = tagService.createTag(tagDto);
        EntityModel<TagDto> resource = tagModelAssembler.toModel(createdTag);
        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TagDto>> updateTag(@PathVariable Long id, @Valid @RequestBody TagDto tagDto) {
        TagDto updatedTag = tagService.updateTag(id, tagDto);
        EntityModel<TagDto> resource = tagModelAssembler.toModel(updatedTag);
        return ResponseEntity
                .ok()
                .location(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
