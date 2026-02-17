package com.manish.hardware.repository;

import com.manish.hardware.model.SupportedLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportedLanguageRepository extends JpaRepository<SupportedLanguage, Long> {

    // Find language by code
    Optional<SupportedLanguage> findByCode(String code);

    // Find all active languages
    List<SupportedLanguage> findByIsActiveTrue();

    // Find all active languages ordered by name
    List<SupportedLanguage> findByIsActiveTrueOrderByNameAsc();

    // Find default language
    Optional<SupportedLanguage> findByIsDefaultTrue();

    // Find default active language
    Optional<SupportedLanguage> findByIsDefaultTrueAndIsActiveTrue();

    // Check if code exists
    boolean existsByCode(String code);

    // Delete by code
    void deleteByCode(String code);

    // Find all ordered by name
    List<SupportedLanguage> findAllByOrderByNameAsc();

    // Reset all default flags
    @Modifying
    @Query("UPDATE SupportedLanguage l SET l.isDefault = false")
    void resetAllDefaults();

    // Set default by code
    @Modifying
    @Query("UPDATE SupportedLanguage l SET l.isDefault = true WHERE l.code = :code")
    void setDefaultByCode(@Param("code") String code);
}
