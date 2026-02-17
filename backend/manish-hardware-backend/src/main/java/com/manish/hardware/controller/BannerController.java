package com.manish.hardware.controller;

import com.manish.hardware.model.Banner;
import com.manish.hardware.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/banners")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    // Get all active banners (public endpoint)
    @GetMapping
    public ResponseEntity<List<Banner>> getActiveBanners() {
        List<Banner> banners = bannerService.getAllActiveBanners();
        return ResponseEntity.ok(banners);
    }

    // Get all banners including inactive (admin endpoint)
    @GetMapping("/all")
    public ResponseEntity<List<Banner>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }

    // Get banner by ID
    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable Long id) {
        Optional<Banner> banner = bannerService.getBannerById(id);
        return banner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create banner (admin endpoint)
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) {
        Banner createdBanner = bannerService.createBanner(banner);
        return ResponseEntity.ok(createdBanner);
    }

    // Update banner (admin endpoint)
    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
        Banner updatedBanner = bannerService.updateBanner(id, banner);
        if (updatedBanner != null) {
            return ResponseEntity.ok(updatedBanner);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete banner (soft delete, admin endpoint)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        boolean deleted = bannerService.deleteBanner(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Hard delete banner (admin endpoint)
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteBanner(@PathVariable Long id) {
        boolean deleted = bannerService.hardDeleteBanner(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Update sort order (admin endpoint)
    @PutMapping("/{id}/sort")
    public ResponseEntity<Void> updateSortOrder(@PathVariable Long id, @RequestParam Integer sortOrder) {
        bannerService.updateSortOrder(id, sortOrder);
        return ResponseEntity.ok().build();
    }

    // Batch update sort order (admin endpoint)
    @PutMapping("/sort-order")
    public ResponseEntity<Void> batchUpdateSortOrder(@RequestBody List<Long> bannerIds) {
        bannerService.batchUpdateSortOrder(bannerIds);
        return ResponseEntity.ok().build();
    }

    // Get all active banners unsorted (public endpoint)
    @GetMapping("/unsorted")
    public ResponseEntity<List<Banner>> getActiveBannersUnsorted() {
        List<Banner> banners = bannerService.getAllActiveBannersUnsorted();
        return ResponseEntity.ok(banners);
    }

    // Get all active banners descending (public endpoint)
    @GetMapping("/desc")
    public ResponseEntity<List<Banner>> getActiveBannersDesc() {
        List<Banner> banners = bannerService.getAllActiveBannersDesc();
        return ResponseEntity.ok(banners);
    }
}
