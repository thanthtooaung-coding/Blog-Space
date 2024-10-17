package com.vinn.blogspace.category.controller;

import com.vinn.blogspace.category.dto.CategoryDto;
import com.vinn.blogspace.category.service.CategoryService;
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
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final RepresentationModelAssembler<CategoryDto, EntityModel<CategoryDto>> categoryAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CategoryDto>>> getAllCategories(Pageable pageable) {
        Page<CategoryDto> categories = categoryService.getAllCategories(pageable);
        PagedModel<EntityModel<CategoryDto>> pagedModel = PagedModel.of(
                categories.getContent().stream()
                        .map(categoryAssembler::toModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(
                        categories.getSize(),
                        categories.getNumber(),
                        categories.getTotalElements(),
                        categories.getTotalPages()
                )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        EntityModel<CategoryDto> resource = categoryAssembler.toModel(category)
                .add(linkTo(methodOn(CategoryController.class).getAllCategories(Pageable.unpaged())).withRel("categories"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        EntityModel<CategoryDto> resource = categoryAssembler.toModel(createdCategory);
        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryDto>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        EntityModel<CategoryDto> resource = categoryAssembler.toModel(updatedCategory);
        return ResponseEntity
                .ok()
                .location(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
