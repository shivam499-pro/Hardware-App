package com.manish.hardware.repository;

import com.manish.hardware.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIsActiveTrueOrderBySortOrderAsc();

    @Query("SELECT c FROM Category c WHERE c.isActive = true ORDER BY c.sortOrder ASC")
    List<Category> findActiveCategories();
}