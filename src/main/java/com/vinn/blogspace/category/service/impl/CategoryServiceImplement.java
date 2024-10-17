package com.vinn.blogspace.category.service.impl;

import com.vinn.blogspace.category.dto.CategoryDto;
import com.vinn.blogspace.category.entity.Category;
import com.vinn.blogspace.category.repository.CategoryRepository;
import com.vinn.blogspace.category.service.CategoryService;
import com.vinn.blogspace.common.services.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImplement extends BaseServiceImpl<Category, Long> implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    protected JpaRepository<Category, Long> getRepository() { return categoryRepository; }

    @Override
    protected String getEntityName() { return "Category"; }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = findById(id);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return findAll(pageable).map(category -> modelMapper.map(category, CategoryDto.class));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = create(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = findById(id);
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = update(id, category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        deleteEntityWithCheck(id, category -> !category.getPosts().isEmpty());
    }
}
