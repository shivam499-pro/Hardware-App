package com.manish.hardware.controller;

import com.manish.hardware.model.AppConfig;
import com.manish.hardware.service.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/config")
@CrossOrigin(origins = "*")
public class AppConfigController {

    @Autowired
    private AppConfigService appConfigService;

    // Get all configs as map (public endpoint for mobile app)
    @GetMapping
    public ResponseEntity<Map<String, String>> getAllConfigsAsMap() {
        Map<String, String> configs = appConfigService.getAllConfigsAsMap();
        return ResponseEntity.ok(configs);
    }

    // Get business config (public endpoint for mobile app)
    @GetMapping("/business")
    public ResponseEntity<Map<String, String>> getBusinessConfig() {
        Map<String, String> configs = appConfigService.getBusinessConfig();
        return ResponseEntity.ok(configs);
    }

    // Get config by key (public endpoint)
    @GetMapping("/{key}")
    public ResponseEntity<AppConfig> getConfigByKey(@PathVariable String key) {
        Optional<AppConfig> config = appConfigService.getConfigByKey(key);
        return config.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get config value by key (public endpoint)
    @GetMapping("/{key}/value")
    public ResponseEntity<String> getConfigValue(@PathVariable String key) {
        String value = appConfigService.getConfigValue(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        }
        return ResponseEntity.notFound().build();
    }

    // Get config value by key with default (public endpoint)
    @GetMapping("/{key}/value/{defaultValue}")
    public ResponseEntity<String> getConfigValueWithDefault(
            @PathVariable String key,
            @PathVariable String defaultValue) {
        String value = appConfigService.getConfigValue(key, defaultValue);
        return ResponseEntity.ok(value);
    }

    // Get multiple configs by keys (public endpoint)
    @PostMapping("/batch")
    public ResponseEntity<Map<String, String>> getConfigsByKeys(@RequestBody List<String> keys) {
        Map<String, String> configs = appConfigService.getConfigsAsMap(keys);
        return ResponseEntity.ok(configs);
    }

    // Admin endpoints

    // Get all configs as list (admin endpoint)
    @GetMapping("/admin/all")
    public ResponseEntity<List<AppConfig>> getAllConfigs() {
        List<AppConfig> configs = appConfigService.getAllConfigs();
        return ResponseEntity.ok(configs);
    }

    // Create config (admin endpoint)
    @PostMapping("/admin")
    public ResponseEntity<AppConfig> createConfig(@RequestBody AppConfig config) {
        try {
            AppConfig createdConfig = appConfigService.createConfig(config);
            return ResponseEntity.ok(createdConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Create or update config (admin endpoint)
    @PutMapping("/admin/{key}")
    public ResponseEntity<AppConfig> saveConfig(@PathVariable String key, @RequestBody Map<String, String> body) {
        String value = body.get("value");
        AppConfig savedConfig = appConfigService.saveConfig(key, value);
        return ResponseEntity.ok(savedConfig);
    }

    // Update config by ID (admin endpoint)
    @PutMapping("/admin/id/{id}")
    public ResponseEntity<AppConfig> updateConfig(@PathVariable Long id, @RequestBody AppConfig config) {
        AppConfig updatedConfig = appConfigService.updateConfig(id, config);
        if (updatedConfig != null) {
            return ResponseEntity.ok(updatedConfig);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete config by ID (admin endpoint)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable Long id) {
        boolean deleted = appConfigService.deleteConfig(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete config by key (admin endpoint)
    @DeleteMapping("/admin/key/{key}")
    public ResponseEntity<Void> deleteConfigByKey(@PathVariable String key) {
        boolean deleted = appConfigService.deleteConfigByKey(key);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Batch save configs (admin endpoint)
    @PostMapping("/admin/batch")
    public ResponseEntity<Void> batchSaveConfigs(@RequestBody Map<String, String> configs) {
        appConfigService.batchSaveConfigs(configs);
        return ResponseEntity.ok().build();
    }
}
