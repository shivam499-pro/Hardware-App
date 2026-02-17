package com.manish.hardware.service;

import com.manish.hardware.model.AppConfig;
import com.manish.hardware.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppConfigService {

    @Autowired
    private AppConfigRepository appConfigRepository;

    // Get all configs
    public List<AppConfig> getAllConfigs() {
        return appConfigRepository.findAllByOrderByKeyNameAsc();
    }

    // Get config by key
    public Optional<AppConfig> getConfigByKey(String keyName) {
        return appConfigRepository.findByKeyName(keyName);
    }

    // Get config value by key
    public String getConfigValue(String keyName) {
        return appConfigRepository.findByKeyName(keyName)
                .map(AppConfig::getValue)
                .orElse(null);
    }

    // Get config value with default
    public String getConfigValue(String keyName, String defaultValue) {
        String value = getConfigValue(keyName);
        return value != null ? value : defaultValue;
    }

    // Get multiple configs by keys
    public List<AppConfig> getConfigsByKeys(List<String> keyNames) {
        return appConfigRepository.findByKeyNameIn(keyNames);
    }

    // Get all configs as a map
    public Map<String, String> getAllConfigsAsMap() {
        Map<String, String> configMap = new HashMap<>();
        appConfigRepository.findAll().forEach(config -> configMap.put(config.getKeyName(), config.getValue()));
        return configMap;
    }

    // Get specific configs as a map
    public Map<String, String> getConfigsAsMap(List<String> keyNames) {
        Map<String, String> configMap = new HashMap<>();
        appConfigRepository.findByKeyNameIn(keyNames)
                .forEach(config -> configMap.put(config.getKeyName(), config.getValue()));
        return configMap;
    }

    // Create or update config
    @Transactional
    public AppConfig saveConfig(String keyName, String value) {
        Optional<AppConfig> existingConfig = appConfigRepository.findByKeyName(keyName);

        if (existingConfig.isPresent()) {
            AppConfig config = existingConfig.get();
            config.setValue(value);
            return appConfigRepository.save(config);
        } else {
            AppConfig newConfig = new AppConfig(keyName, value);
            return appConfigRepository.save(newConfig);
        }
    }

    // Create config (only if not exists)
    @Transactional
    public AppConfig createConfig(AppConfig config) {
        if (appConfigRepository.existsByKeyName(config.getKeyName())) {
            throw new RuntimeException("Config with key '" + config.getKeyName() + "' already exists");
        }
        return appConfigRepository.save(config);
    }

    // Update config
    @Transactional
    public AppConfig updateConfig(Long id, AppConfig configDetails) {
        Optional<AppConfig> optionalConfig = appConfigRepository.findById(id);
        if (optionalConfig.isPresent()) {
            AppConfig config = optionalConfig.get();
            config.setKeyName(configDetails.getKeyName());
            config.setValue(configDetails.getValue());
            return appConfigRepository.save(config);
        }
        return null;
    }

    // Delete config by ID
    @Transactional
    public boolean deleteConfig(Long id) {
        if (appConfigRepository.existsById(id)) {
            appConfigRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete config by key name
    @Transactional
    public boolean deleteConfigByKey(String keyName) {
        if (appConfigRepository.existsByKeyName(keyName)) {
            appConfigRepository.deleteByKeyName(keyName);
            return true;
        }
        return false;
    }

    // Batch save configs
    @Transactional
    public void batchSaveConfigs(Map<String, String> configs) {
        configs.forEach(this::saveConfig);
    }

    // Get business config (commonly used configs for the app)
    public Map<String, String> getBusinessConfig() {
        List<String> businessKeys = List.of(
                "phone_number",
                "whatsapp_number",
                "address",
                "business_hours",
                "business_name",
                "business_email",
                "map_latitude",
                "map_longitude",
                "map_zoom_level");
        return getConfigsAsMap(businessKeys);
    }
}
