package com.manish.hardware.controller;

import com.manish.hardware.model.Product;
import com.manish.hardware.model.ProductTranslation;
import com.manish.hardware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products with pagination
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productService.getAllActiveProducts(pageable);
        return ResponseEntity.ok(products);
    }

    // Get products by category with pagination
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductByIdWithTranslations(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get product by ID and language
    @GetMapping("/{id}/lang/{languageCode}")
    public ResponseEntity<Product> getProductByIdAndLanguage(
            @PathVariable Long id,
            @PathVariable String languageCode) {
        Optional<Product> product = productService.getProductByIdAndLanguage(id, languageCode);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search products
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;

        if (categoryId != null) {
            products = productService.searchProducts(q, categoryId, pageable);
        } else {
            products = productService.searchProducts(q, pageable);
        }

        return ResponseEntity.ok(products);
    }

    // Get translations for a product
    @GetMapping("/{id}/translations")
    public ResponseEntity<List<ProductTranslation>> getProductTranslations(@PathVariable Long id) {
        List<ProductTranslation> translations = productService.getTranslations(id);
        return ResponseEntity.ok(translations);
    }

    // Admin endpoints (should be secured with JWT)
    // Create product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete product (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add translation to product
    @PostMapping("/{id}/translations")
    public ResponseEntity<ProductTranslation> addTranslation(
            @PathVariable Long id,
            @RequestBody ProductTranslation translation) {
        ProductTranslation createdTranslation = productService.addTranslation(id, translation);
        return ResponseEntity.ok(createdTranslation);
    }

    // Update translation
    @PutMapping("/{id}/translations/{languageCode}")
    public ResponseEntity<ProductTranslation> updateTranslation(
            @PathVariable Long id,
            @PathVariable String languageCode,
            @RequestBody ProductTranslation translation) {
        ProductTranslation updatedTranslation = productService.updateTranslation(id, languageCode, translation);
        if (updatedTranslation != null) {
            return ResponseEntity.ok(updatedTranslation);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete translation
    @DeleteMapping("/{id}/translations/{languageCode}")
    public ResponseEntity<Void> deleteTranslation(
            @PathVariable Long id,
            @PathVariable String languageCode) {
        boolean deleted = productService.deleteTranslation(id, languageCode);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get translations by language code
    @GetMapping("/translations/language/{languageCode}")
    public ResponseEntity<List<ProductTranslation>> getTranslationsByLanguage(@PathVariable String languageCode) {
        List<ProductTranslation> translations = productService.getTranslationsByLanguageCode(languageCode);
        return ResponseEntity.ok(translations);
    }

    // Check if translation exists
    @GetMapping("/{id}/translations/{languageCode}/exists")
    public ResponseEntity<Boolean> translationExists(
            @PathVariable Long id,
            @PathVariable String languageCode) {
        boolean exists = productService.translationExists(id, languageCode);
        return ResponseEntity.ok(exists);
    }

    // Count products in category
    @GetMapping("/count/category/{categoryId}")
    public ResponseEntity<Long> countProductsInCategory(@PathVariable Long categoryId) {
        long count = productService.countProductsInCategory(categoryId);
        return ResponseEntity.ok(count);
    }

    // Hard delete product (admin endpoint)
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProduct(@PathVariable Long id) {
        boolean deleted = productService.hardDeleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
