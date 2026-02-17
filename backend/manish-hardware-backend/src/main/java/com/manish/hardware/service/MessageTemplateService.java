package com.manish.hardware.service;

import com.manish.hardware.model.MessageTemplate;
import com.manish.hardware.repository.MessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageTemplateService {

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    // Get all templates
    public List<MessageTemplate> getAllTemplates() {
        return messageTemplateRepository.findAllByOrderByTypeAsc();
    }

    // Get template by ID
    public Optional<MessageTemplate> getTemplateById(Long id) {
        return messageTemplateRepository.findById(id);
    }

    // Get template by type and language
    public Optional<MessageTemplate> getTemplateByTypeAndLanguage(String type, String languageCode) {
        return messageTemplateRepository.findByTypeAndLanguageCode(type, languageCode);
    }

    // Get template content by type and language
    public String getTemplateContent(String type, String languageCode) {
        return messageTemplateRepository.findByTypeAndLanguageCode(type, languageCode)
                .map(MessageTemplate::getTemplate)
                .orElse(null);
    }

    // Get all templates by type
    public List<MessageTemplate> getTemplatesByType(String type) {
        return messageTemplateRepository.findByType(type);
    }

    // Get all templates by language
    public List<MessageTemplate> getTemplatesByLanguage(String languageCode) {
        return messageTemplateRepository.findByLanguageCode(languageCode);
    }

    // Get all distinct types
    public List<String> getAllTemplateTypes() {
        return messageTemplateRepository.findAllDistinctTypes();
    }

    // Get templates as map by type for a language
    public Map<String, String> getTemplatesAsMapByLanguage(String languageCode) {
        Map<String, String> templateMap = new HashMap<>();
        messageTemplateRepository.findByLanguageCode(languageCode)
                .forEach(template -> templateMap.put(template.getType(), template.getTemplate()));
        return templateMap;
    }

    // Create template
    @Transactional
    public MessageTemplate createTemplate(MessageTemplate template) {
        if (messageTemplateRepository.existsByTypeAndLanguageCode(template.getType(), template.getLanguageCode())) {
            throw new RuntimeException("Template with type '" + template.getType() +
                    "' and language '" + template.getLanguageCode() + "' already exists");
        }
        return messageTemplateRepository.save(template);
    }

    // Create or update template
    @Transactional
    public MessageTemplate saveTemplate(String type, String languageCode, String templateContent) {
        Optional<MessageTemplate> existingTemplate = messageTemplateRepository.findByTypeAndLanguageCode(type,
                languageCode);

        if (existingTemplate.isPresent()) {
            MessageTemplate template = existingTemplate.get();
            template.setTemplate(templateContent);
            return messageTemplateRepository.save(template);
        } else {
            MessageTemplate newTemplate = new MessageTemplate(type, languageCode, templateContent);
            return messageTemplateRepository.save(newTemplate);
        }
    }

    // Update template
    @Transactional
    public MessageTemplate updateTemplate(Long id, MessageTemplate templateDetails) {
        Optional<MessageTemplate> optionalTemplate = messageTemplateRepository.findById(id);
        if (optionalTemplate.isPresent()) {
            MessageTemplate template = optionalTemplate.get();
            template.setType(templateDetails.getType());
            template.setLanguageCode(templateDetails.getLanguageCode());
            template.setTemplate(templateDetails.getTemplate());
            return messageTemplateRepository.save(template);
        }
        return null;
    }

    // Delete template by ID
    @Transactional
    public boolean deleteTemplate(Long id) {
        if (messageTemplateRepository.existsById(id)) {
            messageTemplateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete template by type and language
    @Transactional
    public boolean deleteTemplateByTypeAndLanguage(String type, String languageCode) {
        if (messageTemplateRepository.existsByTypeAndLanguageCode(type, languageCode)) {
            messageTemplateRepository.deleteByTypeAndLanguageCode(type, languageCode);
            return true;
        }
        return false;
    }

    // Render template with variables
    public String renderTemplate(String type, String languageCode, Map<String, String> variables) {
        String template = getTemplateContent(type, languageCode);
        if (template == null) {
            // Fallback to English if template not found in requested language
            template = getTemplateContent(type, "en");
        }
        if (template == null) {
            return null;
        }

        // Replace placeholders with values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return template;
    }

    // Render WhatsApp quote template
    public String renderWhatsAppQuoteTemplate(String languageCode, String productName,
            String quantity, String name, String location) {
        Map<String, String> variables = new HashMap<>();
        variables.put("product", productName);
        variables.put("quantity", quantity);
        variables.put("name", name);
        variables.put("location", location);

        return renderTemplate("whatsapp_quote", languageCode, variables);
    }
}
