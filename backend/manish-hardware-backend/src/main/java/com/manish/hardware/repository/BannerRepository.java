package com.manish.hardware.repository;

import com.manish.hardware.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    // Find all active banners
    List<Banner> findByIsActiveTrue();

    // Find all active banners ordered by sort order
    List<Banner> findByIsActiveTrueOrderBySortOrderAsc();

    // Find all banners ordered by sort order
    List<Banner> findAllByOrderBySortOrderAsc();

    // Find banners by active status ordered by sort order
    List<Banner> findByIsActiveTrueOrderBySortOrderDesc();
}
