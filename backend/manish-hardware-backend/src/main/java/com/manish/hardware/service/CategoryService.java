package com.manish.hardware.service;

import com.manish.hardware.model.Category;
import com.manish.hardware.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllActiveCategories() {
        return categoryRepository.findActiveCategories();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Get all active categories ordered by sort order
    public List<Category> getAllActiveCategoriesOrdered() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrderAsc();
    }
}