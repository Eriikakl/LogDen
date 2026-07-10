package com.logden.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Category;
import com.logden.backend.domain.CategoryRepository;
import com.logden.backend.exception.ResourceAlreadyExistsException;
import com.logden.backend.exception.ResourceNotFoundException;

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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    // Adminille endpointit kategorian lisäykseen, muokkaukseen ja poistoon

    public Category addCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Category name already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Optional<Category> existingCategory = categoryRepository.findByName(updatedCategory.getName());
        if (existingCategory.isPresent() && !existingCategory.get().getCategoryId().equals(id)) {
            throw new ResourceAlreadyExistsException("Category name already exists");
        }
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

}
