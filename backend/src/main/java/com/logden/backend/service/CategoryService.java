package com.logden.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Category;
import com.logden.backend.domain.CategoryRepository;


@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

     public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
