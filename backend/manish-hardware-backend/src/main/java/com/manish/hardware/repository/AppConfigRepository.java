package com.manish.hardware.repository;

import com.manish.hardware.model.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    // Find config by key name
    Optional<AppConfig> findByKeyName(String keyName);

    // Find all configs by key names
    List<AppConfig> findByKeyNameIn(List<String> keyNames);

    // Check if key exists
    boolean existsByKeyName(String keyName);

    // Delete by key name
    void deleteByKeyName(String keyName);

    // Find all configs ordered by key name
    List<AppConfig> findAllByOrderByKeyNameAsc();
}
