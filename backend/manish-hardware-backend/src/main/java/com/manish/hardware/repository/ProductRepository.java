package com.manish.hardware.repository;

import com.manish.hardware.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all active products
    List<Product> findByIsActiveTrue();

    // Find all active products with pagination
    Page<Product> findByIsActiveTrue(Pageable pageable);

    // Find products by category
    List<Product> findByCategoryIdAndIsActiveTrue(Long categoryId);

    // Find products by category with pagination
    Page<Product> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);

    // Find product by id with translations
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.translations WHERE p.id = :id AND p.isActive = true")
    Optional<Product> findByIdWithTranslations(@Param("id") Long id);

    // Find product by id and language code
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.translations t WHERE p.id = :id AND p.isActive = true AND t.languageCode = :languageCode")
    Optional<Product> findByIdAndLanguageCode(@Param("id") Long id, @Param("languageCode") String languageCode);

    // Search products by name in translations
    @Query("SELECT DISTINCT p FROM Product p JOIN p.translations t WHERE p.isActive = true AND LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Search products by name in translations within a category
    @Query("SELECT DISTINCT p FROM Product p JOIN p.translations t WHERE p.isActive = true AND p.categoryId = :categoryId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> searchByNameAndCategory(@Param("searchTerm") String searchTerm, @Param("categoryId") Long categoryId,
            Pageable pageable);

    // Count active products by category
    long countByCategoryIdAndIsActiveTrue(Long categoryId);

    // Count all active products
    long countByIsActiveTrue();
}
