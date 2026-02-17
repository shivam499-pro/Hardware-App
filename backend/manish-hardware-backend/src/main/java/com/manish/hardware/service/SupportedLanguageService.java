package com.manish.hardware.service;

import com.manish.hardware.model.SupportedLanguage;
import com.manish.hardware.repository.SupportedLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupportedLanguageService {

    @Autowired
    private SupportedLanguageRepository supportedLanguageRepository;

    // Get all languages
    public List<SupportedLanguage> getAllLanguages() {
        return supportedLanguageRepository.findAllByOrderByNameAsc();
    }

    // Get all active languages
    public List<SupportedLanguage> getActiveLanguages() {
        return supportedLanguageRepository.findByIsActiveTrueOrderByNameAsc();
    }

    // Get language by ID
    public Optional<SupportedLanguage> getLanguageById(Long id) {
        return supportedLanguageRepository.findById(id);
    }

    // Get language by code
    public Optional<SupportedLanguage> getLanguageByCode(String code) {
        return supportedLanguageRepository.findByCode(code);
    }

    // Get default language
    public Optional<SupportedLanguage> getDefaultLanguage() {
        return supportedLanguageRepository.findByIsDefaultTrueAndIsActiveTrue();
    }

    // Get default language code
    public String getDefaultLanguageCode() {
        return supportedLanguageRepository.findByIsDefaultTrueAndIsActiveTrue()
                .map(SupportedLanguage::getCode)
                .orElse("en");
    }

    // Create language
    @Transactional
    public SupportedLanguage createLanguage(SupportedLanguage language) {
        if (supportedLanguageRepository.existsByCode(language.getCode())) {
            throw new RuntimeException("Language with code '" + language.getCode() + "' already exists");
        }

        // If this is set as default, unset other defaults
        if (Boolean.TRUE.equals(language.getIsDefault())) {
            supportedLanguageRepository.resetAllDefaults();
        }

        return supportedLanguageRepository.save(language);
    }

    // Update language
    @Transactional
    public SupportedLanguage updateLanguage(Long id, SupportedLanguage languageDetails) {
        Optional<SupportedLanguage> optionalLanguage = supportedLanguageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            SupportedLanguage language = optionalLanguage.get();

            // Check if new code already exists for a different language
            if (!language.getCode().equals(languageDetails.getCode()) &&
                    supportedLanguageRepository.existsByCode(languageDetails.getCode())) {
                throw new RuntimeException("Language with code '" + languageDetails.getCode() + "' already exists");
            }

            language.setCode(languageDetails.getCode());
            language.setName(languageDetails.getName());
            language.setIsActive(languageDetails.getIsActive());

            // Handle default flag
            if (Boolean.TRUE.equals(languageDetails.getIsDefault()) &&
                    !Boolean.TRUE.equals(language.getIsDefault())) {
                supportedLanguageRepository.resetAllDefaults();
                language.setIsDefault(true);
            }

            return supportedLanguageRepository.save(language);
        }
        return null;
    }

    // Set default language
    @Transactional
    public boolean setDefaultLanguage(String code) {
        Optional<SupportedLanguage> optionalLanguage = supportedLanguageRepository.findByCode(code);
        if (optionalLanguage.isPresent()) {
            SupportedLanguage language = optionalLanguage.get();
            if (Boolean.TRUE.equals(language.getIsActive())) {
                supportedLanguageRepository.resetAllDefaults();
                language.setIsDefault(true);
                supportedLanguageRepository.save(language);
                return true;
            }
        }
        return false;
    }

    // Delete language
    @Transactional
    public boolean deleteLanguage(Long id) {
        if (supportedLanguageRepository.existsById(id)) {
            SupportedLanguage language = supportedLanguageRepository.findById(id).get();
            // Don't allow deleting the default language
            if (Boolean.TRUE.equals(language.getIsDefault())) {
                throw new RuntimeException("Cannot delete the default language");
            }
            supportedLanguageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete language by code
    @Transactional
    public boolean deleteLanguageByCode(String code) {
        Optional<SupportedLanguage> optionalLanguage = supportedLanguageRepository.findByCode(code);
        if (optionalLanguage.isPresent()) {
            SupportedLanguage language = optionalLanguage.get();
            // Don't allow deleting the default language
            if (Boolean.TRUE.equals(language.getIsDefault())) {
                throw new RuntimeException("Cannot delete the default language");
            }
            supportedLanguageRepository.deleteByCode(code);
            return true;
        }
        return false;
    }

    // Toggle language active status
    @Transactional
    public SupportedLanguage toggleLanguageActive(Long id) {
        Optional<SupportedLanguage> optionalLanguage = supportedLanguageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            SupportedLanguage language = optionalLanguage.get();
            // Don't allow deactivating the default language
            if (Boolean.TRUE.equals(language.getIsDefault()) && Boolean.TRUE.equals(language.getIsActive())) {
                throw new RuntimeException("Cannot deactivate the default language");
            }
            language.setIsActive(!language.getIsActive());
            return supportedLanguageRepository.save(language);
        }
        return null;
    }

    // Check if language is supported
    public boolean isLanguageSupported(String code) {
        return supportedLanguageRepository.findByCode(code)
                .map(lang -> Boolean.TRUE.equals(lang.getIsActive()))
                .orElse(false);
    }

    // Get all active languages (unsorted)
    public List<SupportedLanguage> getActiveLanguagesUnsorted() {
        return supportedLanguageRepository.findByIsActiveTrue();
    }

    // Get default language (regardless of active status)
    public Optional<SupportedLanguage> getDefaultLanguageAny() {
        return supportedLanguageRepository.findByIsDefaultTrue();
    }

    // Set default language by code directly
    @Transactional
    public boolean setDefaultLanguageDirect(String code) {
        if (supportedLanguageRepository.existsByCode(code)) {
            supportedLanguageRepository.resetAllDefaults();
            supportedLanguageRepository.setDefaultByCode(code);
            return true;
        }
        return false;
    }
}
