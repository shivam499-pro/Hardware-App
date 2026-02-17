package com.manish.hardware.service;

import com.manish.hardware.model.Category;
import com.manish.hardware.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Cement");
        testCategory.setDescription("High quality cement");
        testCategory.setSortOrder(1);
        testCategory.setIsActive(true);
    }

    @Test
    void testGetAllActiveCategories() {
        when(categoryRepository.findByIsActiveTrueOrderBySortOrderAsc()).thenReturn(Arrays.asList(testCategory));

        List<Category> categories = categoryService.getAllActiveCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Cement", categories.get(0).getName());

        verify(categoryRepository, times(1)).findByIsActiveTrueOrderBySortOrderAsc();
    }

    @Test
    void testGetCategoryById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Optional<Category> result = categoryService.getCategoryById(1L);

        assertTrue(result.isPresent());
        assertEquals("Cement", result.get().getName());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryById(999L);

        assertFalse(result.isPresent());

        verify(categoryRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        Category result = categoryService.saveCategory(testCategory);

        assertNotNull(result);
        assertEquals("Cement", result.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllActiveCategoriesOrdered() {
        when(categoryRepository.findActiveCategories()).thenReturn(Arrays.asList(testCategory));

        List<Category> categories = categoryService.getAllActiveCategoriesOrdered();

        assertNotNull(categories);
        assertEquals(1, categories.size());

        verify(categoryRepository, times(1)).findActiveCategories();
    }
}
