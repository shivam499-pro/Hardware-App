package com.manish.hardware.controller;

import com.manish.hardware.model.MessageTemplate;
import com.manish.hardware.service.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/templates")
@CrossOrigin(origins = "*")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    // Get all templates (admin endpoint)
    @GetMapping
    public ResponseEntity<List<MessageTemplate>> getAllTemplates() {
        List<MessageTemplate> templates = messageTemplateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    // Get all template types
    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTemplateTypes() {
        List<String> types = messageTemplateService.getAllTemplateTypes();
        return ResponseEntity.ok(types);
    }

    // Get template by ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageTemplate> getTemplateById(@PathVariable Long id) {
        Optional<MessageTemplate> template = messageTemplateService.getTemplateById(id);
        return template.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get template by type and language (public endpoint for mobile app)
    @GetMapping("/{type}/{languageCode}")
    public ResponseEntity<MessageTemplate> getTemplateByTypeAndLanguage(
            @PathVariable String type,
            @PathVariable String languageCode) {
        Optional<MessageTemplate> template = messageTemplateService.getTemplateByTypeAndLanguage(type, languageCode);
        return template.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get template content by type and language (public endpoint)
    @GetMapping("/{type}/{languageCode}/content")
    public ResponseEntity<String> getTemplateContent(
            @PathVariable String type,
            @PathVariable String languageCode) {
        String content = messageTemplateService.getTemplateContent(type, languageCode);
        if (content != null) {
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all templates by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<MessageTemplate>> getTemplatesByType(@PathVariable String type) {
        List<MessageTemplate> templates = messageTemplateService.getTemplatesByType(type);
        return ResponseEntity.ok(templates);
    }

    // Get all templates by language
    @GetMapping("/language/{languageCode}")
    public ResponseEntity<List<MessageTemplate>> getTemplatesByLanguage(@PathVariable String languageCode) {
        List<MessageTemplate> templates = messageTemplateService.getTemplatesByLanguage(languageCode);
        return ResponseEntity.ok(templates);
    }

    // Get templates as map by language (public endpoint for mobile app)
    @GetMapping("/map/{languageCode}")
    public ResponseEntity<Map<String, String>> getTemplatesAsMapByLanguage(@PathVariable String languageCode) {
        Map<String, String> templates = messageTemplateService.getTemplatesAsMapByLanguage(languageCode);
        return ResponseEntity.ok(templates);
    }

    // Render template with variables (public endpoint for mobile app)
    @PostMapping("/render")
    public ResponseEntity<String> renderTemplate(
            @RequestParam String type,
            @RequestParam String languageCode,
            @RequestBody Map<String, String> variables) {
        String rendered = messageTemplateService.renderTemplate(type, languageCode, variables);
        if (rendered != null) {
            return ResponseEntity.ok(rendered);
        }
        return ResponseEntity.notFound().build();
    }

    // Render WhatsApp quote template (public endpoint for mobile app)
    @PostMapping("/render/whatsapp-quote")
    public ResponseEntity<String> renderWhatsAppQuoteTemplate(
            @RequestParam String languageCode,
            @RequestBody Map<String, String> params) {
        String rendered = messageTemplateService.renderWhatsAppQuoteTemplate(
                languageCode,
                params.get("product"),
                params.get("quantity"),
                params.get("name"),
                params.get("location"));
        if (rendered != null) {
            return ResponseEntity.ok(rendered);
        }
        return ResponseEntity.notFound().build();
    }

    // Admin endpoints

    // Create template (admin endpoint)
    @PostMapping("/admin")
    public ResponseEntity<MessageTemplate> createTemplate(@RequestBody MessageTemplate template) {
        try {
            MessageTemplate createdTemplate = messageTemplateService.createTemplate(template);
            return ResponseEntity.ok(createdTemplate);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Create or update template (admin endpoint)
    @PutMapping("/admin/{type}/{languageCode}")
    public ResponseEntity<MessageTemplate> saveTemplate(
            @PathVariable String type,
            @PathVariable String languageCode,
            @RequestBody Map<String, String> body) {
        String templateContent = body.get("template");
        MessageTemplate savedTemplate = messageTemplateService.saveTemplate(type, languageCode, templateContent);
        return ResponseEntity.ok(savedTemplate);
    }

    // Update template by ID (admin endpoint)
    @PutMapping("/admin/{id}")
    public ResponseEntity<MessageTemplate> updateTemplate(@PathVariable Long id,
            @RequestBody MessageTemplate template) {
        MessageTemplate updatedTemplate = messageTemplateService.updateTemplate(id, template);
        if (updatedTemplate != null) {
            return ResponseEntity.ok(updatedTemplate);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete template by ID (admin endpoint)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        boolean deleted = messageTemplateService.deleteTemplate(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete template by type and language (admin endpoint)
    @DeleteMapping("/admin/{type}/{languageCode}")
    public ResponseEntity<Void> deleteTemplateByTypeAndLanguage(
            @PathVariable String type,
            @PathVariable String languageCode) {
        boolean deleted = messageTemplateService.deleteTemplateByTypeAndLanguage(type, languageCode);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
