package com.manish.hardware.controller;

import com.manish.hardware.model.SupportedLanguage;
import com.manish.hardware.service.SupportedLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/languages")
@CrossOrigin(origins = "*")
public class SupportedLanguageController {

    @Autowired
    private SupportedLanguageService supportedLanguageService;

    // Get all active languages (public endpoint for mobile app)
    @GetMapping
    public ResponseEntity<List<SupportedLanguage>> getActiveLanguages() {
        List<SupportedLanguage> languages = supportedLanguageService.getActiveLanguages();
        return ResponseEntity.ok(languages);
    }

    // Get default language (public endpoint for mobile app)
    @GetMapping("/default")
    public ResponseEntity<SupportedLanguage> getDefaultLanguage() {
        Optional<SupportedLanguage> language = supportedLanguageService.getDefaultLanguage();
        return language.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get default language code (public endpoint for mobile app)
    @GetMapping("/default/code")
    public ResponseEntity<String> getDefaultLanguageCode() {
        String code = supportedLanguageService.getDefaultLanguageCode();
        return ResponseEntity.ok(code);
    }

    // Check if language is supported (public endpoint for mobile app)
    @GetMapping("/{code}/supported")
    public ResponseEntity<Boolean> isLanguageSupported(@PathVariable String code) {
        boolean supported = supportedLanguageService.isLanguageSupported(code);
        return ResponseEntity.ok(supported);
    }

    // Get language by code (public endpoint)
    @GetMapping("/{code}")
    public ResponseEntity<SupportedLanguage> getLanguageByCode(@PathVariable String code) {
        Optional<SupportedLanguage> language = supportedLanguageService.getLanguageByCode(code);
        return language.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin endpoints

    // Get all languages including inactive (admin endpoint)
    @GetMapping("/admin/all")
    public ResponseEntity<List<SupportedLanguage>> getAllLanguages() {
        List<SupportedLanguage> languages = supportedLanguageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    // Get language by ID (admin endpoint)
    @GetMapping("/admin/{id}")
    public ResponseEntity<SupportedLanguage> getLanguageById(@PathVariable Long id) {
        Optional<SupportedLanguage> language = supportedLanguageService.getLanguageById(id);
        return language.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create language (admin endpoint)
    @PostMapping("/admin")
    public ResponseEntity<SupportedLanguage> createLanguage(@RequestBody SupportedLanguage language) {
        try {
            SupportedLanguage createdLanguage = supportedLanguageService.createLanguage(language);
            return ResponseEntity.ok(createdLanguage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update language (admin endpoint)
    @PutMapping("/admin/{id}")
    public ResponseEntity<SupportedLanguage> updateLanguage(@PathVariable Long id,
            @RequestBody SupportedLanguage language) {
        try {
            SupportedLanguage updatedLanguage = supportedLanguageService.updateLanguage(id, language);
            if (updatedLanguage != null) {
                return ResponseEntity.ok(updatedLanguage);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Set default language (admin endpoint)
    @PutMapping("/admin/default/{code}")
    public ResponseEntity<Void> setDefaultLanguage(@PathVariable String code) {
        boolean success = supportedLanguageService.setDefaultLanguage(code);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Toggle language active status (admin endpoint)
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<SupportedLanguage> toggleLanguageActive(@PathVariable Long id) {
        try {
            SupportedLanguage updatedLanguage = supportedLanguageService.toggleLanguageActive(id);
            if (updatedLanguage != null) {
                return ResponseEntity.ok(updatedLanguage);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete language (admin endpoint)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        try {
            boolean deleted = supportedLanguageService.deleteLanguage(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete language by code (admin endpoint)
    @DeleteMapping("/admin/code/{code}")
    public ResponseEntity<Void> deleteLanguageByCode(@PathVariable String code) {
        try {
            boolean deleted = supportedLanguageService.deleteLanguageByCode(code);
            if (deleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all active languages unsorted (public endpoint)
    @GetMapping("/active")
    public ResponseEntity<List<SupportedLanguage>> getActiveLanguagesUnsorted() {
        List<SupportedLanguage> languages = supportedLanguageService.getActiveLanguagesUnsorted();
        return ResponseEntity.ok(languages);
    }

    // Get default language any (public endpoint)
    @GetMapping("/default/any")
    public ResponseEntity<SupportedLanguage> getDefaultLanguageAny() {
        Optional<SupportedLanguage> language = supportedLanguageService.getDefaultLanguageAny();
        return language.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Set default language direct (admin endpoint)
    @PutMapping("/admin/default-direct/{code}")
    public ResponseEntity<Void> setDefaultLanguageDirect(@PathVariable String code) {
        boolean success = supportedLanguageService.setDefaultLanguageDirect(code);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
