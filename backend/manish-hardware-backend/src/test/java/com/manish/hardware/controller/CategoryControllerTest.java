package com.manish.hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.model.Category;
import com.manish.hardware.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    void testGetAllCategories_Success() throws Exception {
        when(categoryService.getAllActiveCategories()).thenReturn(Arrays.asList(testCategory));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cement"))
                .andExpect(jsonPath("$[0].description").value("High quality cement"));

        verify(categoryService, times(1)).getAllActiveCategories();
    }

    @Test
    void testGetCategoryById_Success() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(testCategory));

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cement"));

        verify(categoryService, times(1)).getCategoryById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() throws Exception {
        when(categoryService.getCategoryById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateCategory_Success() throws Exception {
        when(categoryService.saveCategory(any(Category.class))).thenReturn(testCategory);

        mockMvc.perform(post("/api/v1/categories")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cement"));

        verify(categoryService, times(1)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateCategory_Success() throws Exception {
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Cement");
        updatedCategory.setDescription("Updated description");

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryService.saveCategory(any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/v1/categories/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Cement"));

        verify(categoryService, times(1)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteCategory_Success() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(testCategory));
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/categories/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(1L);
    }

    @Test
    void testGetAllCategoriesOrdered_Success() throws Exception {
        when(categoryService.getAllActiveCategoriesOrdered()).thenReturn(Arrays.asList(testCategory));

        mockMvc.perform(get("/api/v1/categories/ordered"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cement"));

        verify(categoryService, times(1)).getAllActiveCategoriesOrdered();
    }
}
