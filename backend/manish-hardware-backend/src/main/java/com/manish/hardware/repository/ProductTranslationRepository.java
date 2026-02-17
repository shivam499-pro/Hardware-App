package com.manish.hardware.repository;

import com.manish.hardware.model.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Long> {

    // Find all translations for a product
    List<ProductTranslation> findByProductId(Long productId);

    // Find translation by product and language
    Optional<ProductTranslation> findByProductIdAndLanguageCode(Long productId, String languageCode);

    // Find all translations by language code
    List<ProductTranslation> findByLanguageCode(String languageCode);

    // Delete all translations for a product
    void deleteByProductId(Long productId);

    // Check if translation exists
    boolean existsByProductIdAndLanguageCode(Long productId, String languageCode);
}
