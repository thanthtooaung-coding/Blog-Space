package com.vinn.blogspace.category.service;

import com.vinn.blogspace.category.dto.CategoryDto;
import com.vinn.blogspace.category.entity.Category;
import com.vinn.blogspace.common.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService extends BaseService<Category, Long> {
    CategoryDto getCategoryById(Long id);
    Page<CategoryDto> getAllCategories(Pageable pageable);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);
}
