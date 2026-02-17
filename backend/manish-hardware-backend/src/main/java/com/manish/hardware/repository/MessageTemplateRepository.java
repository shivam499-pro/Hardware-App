package com.manish.hardware.repository;

import com.manish.hardware.model.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {

    // Find template by type and language
    Optional<MessageTemplate> findByTypeAndLanguageCode(String type, String languageCode);

    // Find all templates by type
    List<MessageTemplate> findByType(String type);

    // Find all templates by language
    List<MessageTemplate> findByLanguageCode(String languageCode);

    // Check if template exists
    boolean existsByTypeAndLanguageCode(String type, String languageCode);

    // Delete by type and language
    void deleteByTypeAndLanguageCode(String type, String languageCode);

    // Find all templates ordered by type
    List<MessageTemplate> findAllByOrderByTypeAsc();

    // Find all distinct types
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT m.type FROM MessageTemplate m")
    List<String> findAllDistinctTypes();
}
