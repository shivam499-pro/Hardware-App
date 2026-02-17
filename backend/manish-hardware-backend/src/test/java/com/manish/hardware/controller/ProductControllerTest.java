package com.manish.hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.model.Product;
import com.manish.hardware.model.ProductTranslation;
import com.manish.hardware.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product testProduct;
    private ProductTranslation testTranslation;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setCategoryId(1L);
        testProduct.setBrand("Test Brand");
        testProduct.setIsActive(true);

        testTranslation = new ProductTranslation();
        testTranslation.setId(1L);
        testTranslation.setProductId(1L);
        testTranslation.setLanguageCode("en");
        testTranslation.setName("Test Product");
        testTranslation.setDescription("Test Description");
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productService.getAllActiveProducts(any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Test Brand"));

        verify(productService, times(1)).getAllActiveProducts(any(Pageable.class));
    }

    @Test
    void testGetProductsByCategory_Success() throws Exception {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productService.getProductsByCategory(anyLong(), any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/v1/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoryId").value(1));

        verify(productService, times(1)).getProductsByCategory(anyLong(), any(Pageable.class));
    }

    @Test
    void testGetProductById_Success() throws Exception {
        when(productService.getProductByIdWithTranslations(1L)).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Test Brand"));

        verify(productService, times(1)).getProductByIdWithTranslations(1L);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductByIdWithTranslations(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductByIdAndLanguage_Success() throws Exception {
        when(productService.getProductByIdAndLanguage(1L, "en")).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/api/v1/products/1/lang/en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Test Brand"));

        verify(productService, times(1)).getProductByIdAndLanguage(1L, "en");
    }

    @Test
    void testSearchProducts_Success() throws Exception {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productService.searchProducts(anyString(), any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/v1/products/search?q=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Test Brand"));

        verify(productService, times(1)).searchProducts(anyString(), any(Pageable.class));
    }

    @Test
    void testGetProductTranslations_Success() throws Exception {
        List<ProductTranslation> translations = Arrays.asList(testTranslation);
        when(productService.getTranslations(1L)).thenReturn(translations);

        mockMvc.perform(get("/api/v1/products/1/translations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"));

        verify(productService, times(1)).getTranslations(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateProduct_Success() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(post("/api/v1/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Test Brand"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateProduct_Success() throws Exception {
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(put("/api/v1/products/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Test Brand"));

        verify(productService, times(1)).updateProduct(anyLong(), any(Product.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteProduct_Success() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/products/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testAddTranslation_Success() throws Exception {
        when(productService.addTranslation(anyLong(), any(ProductTranslation.class))).thenReturn(testTranslation);

        mockMvc.perform(post("/api/v1/products/1/translations")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTranslation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).addTranslation(anyLong(), any(ProductTranslation.class));
    }

    @Test
    void testGetTranslationsByLanguage_Success() throws Exception {
        List<ProductTranslation> translations = Arrays.asList(testTranslation);
        when(productService.getTranslationsByLanguageCode("en")).thenReturn(translations);

        mockMvc.perform(get("/api/v1/products/translations/language/en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].languageCode").value("en"));

        verify(productService, times(1)).getTranslationsByLanguageCode("en");
    }

    @Test
    void testTranslationExists_Success() throws Exception {
        when(productService.translationExists(1L, "en")).thenReturn(true);

        mockMvc.perform(get("/api/v1/products/1/translations/en/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(productService, times(1)).translationExists(1L, "en");
    }

    @Test
    void testCountProductsInCategory_Success() throws Exception {
        when(productService.countProductsInCategory(1L)).thenReturn(5L);

        mockMvc.perform(get("/api/v1/products/count/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(productService, times(1)).countProductsInCategory(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testHardDeleteProduct_Success() throws Exception {
        when(productService.hardDeleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/products/1/hard")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(productService, times(1)).hardDeleteProduct(1L);
    }
}
