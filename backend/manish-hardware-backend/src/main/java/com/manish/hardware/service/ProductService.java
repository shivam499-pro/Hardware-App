package com.manish.hardware.service;

import com.manish.hardware.model.Product;
import com.manish.hardware.model.ProductTranslation;
import com.manish.hardware.repository.ProductRepository;
import com.manish.hardware.repository.ProductTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTranslationRepository translationRepository;

    // Get all active products
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    // Get all active products with pagination
    public Page<Product> getAllActiveProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable);
    }

    // Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId);
    }

    // Get products by category with pagination
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable);
    }

    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Get product by ID with translations
    public Optional<Product> getProductByIdWithTranslations(Long id) {
        return productRepository.findByIdWithTranslations(id);
    }

    // Get product by ID and language
    public Optional<Product> getProductByIdAndLanguage(Long id, String languageCode) {
        return productRepository.findByIdAndLanguageCode(id, languageCode);
    }

    // Search products by name
    public Page<Product> searchProducts(String searchTerm, Pageable pageable) {
        return productRepository.searchByName(searchTerm, pageable);
    }

    // Search products by name within category
    public Page<Product> searchProducts(String searchTerm, Long categoryId, Pageable pageable) {
        return productRepository.searchByNameAndCategory(searchTerm, categoryId, pageable);
    }

    // Create product
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Update product
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setCategoryId(productDetails.getCategoryId());
            product.setBrand(productDetails.getBrand());
            product.setImageUrl(productDetails.getImageUrl());
            product.setTechnicalSpecs(productDetails.getTechnicalSpecs());
            product.setUsageInfo(productDetails.getUsageInfo());
            product.setIsActive(productDetails.getIsActive());
            return productRepository.save(product);
        }
        return null;
    }

    // Delete product (soft delete)
    @Transactional
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setIsActive(false);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    // Hard delete product
    @Transactional
    public boolean hardDeleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            translationRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add translation to product
    @Transactional
    public ProductTranslation addTranslation(Long productId, ProductTranslation translation) {
        translation.setProductId(productId);
        return translationRepository.save(translation);
    }

    // Update translation
    @Transactional
    public ProductTranslation updateTranslation(Long productId, String languageCode,
            ProductTranslation translationDetails) {
        Optional<ProductTranslation> optionalTranslation = translationRepository
                .findByProductIdAndLanguageCode(productId, languageCode);
        if (optionalTranslation.isPresent()) {
            ProductTranslation translation = optionalTranslation.get();
            translation.setName(translationDetails.getName());
            translation.setDescription(translationDetails.getDescription());
            return translationRepository.save(translation);
        }
        return null;
    }

    // Delete translation
    @Transactional
    public boolean deleteTranslation(Long productId, String languageCode) {
        Optional<ProductTranslation> optionalTranslation = translationRepository
                .findByProductIdAndLanguageCode(productId, languageCode);
        if (optionalTranslation.isPresent()) {
            translationRepository.delete(optionalTranslation.get());
            return true;
        }
        return false;
    }

    // Get translations for a product
    public List<ProductTranslation> getTranslations(Long productId) {
        return translationRepository.findByProductId(productId);
    }

    // Count products in category
    public long countProductsInCategory(Long categoryId) {
        return productRepository.countByCategoryIdAndIsActiveTrue(categoryId);
    }

    // Get translations by language code
    public List<ProductTranslation> getTranslationsByLanguageCode(String languageCode) {
        return translationRepository.findByLanguageCode(languageCode);
    }

    // Check if translation exists
    public boolean translationExists(Long productId, String languageCode) {
        return translationRepository.existsByProductIdAndLanguageCode(productId, languageCode);
    }

    // Get count of all active products
    public long getActiveProductCount() {
        return productRepository.countByIsActiveTrue();
    }
}
