package com.manish.hardware.service;

import com.manish.hardware.model.Banner;
import com.manish.hardware.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    // Get all active banners ordered by sort order
    public List<Banner> getAllActiveBanners() {
        return bannerRepository.findByIsActiveTrueOrderBySortOrderAsc();
    }

    // Get all banners (for admin)
    public List<Banner> getAllBanners() {
        return bannerRepository.findAllByOrderBySortOrderAsc();
    }

    // Get banner by ID
    public Optional<Banner> getBannerById(Long id) {
        return bannerRepository.findById(id);
    }

    // Create banner
    @Transactional
    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    // Update banner
    @Transactional
    public Banner updateBanner(Long id, Banner bannerDetails) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            banner.setTitle(bannerDetails.getTitle());
            banner.setImageUrl(bannerDetails.getImageUrl());
            banner.setLinkUrl(bannerDetails.getLinkUrl());
            banner.setSortOrder(bannerDetails.getSortOrder());
            banner.setIsActive(bannerDetails.getIsActive());
            return bannerRepository.save(banner);
        }
        return null;
    }

    // Delete banner (soft delete)
    @Transactional
    public boolean deleteBanner(Long id) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            banner.setIsActive(false);
            bannerRepository.save(banner);
            return true;
        }
        return false;
    }

    // Hard delete banner
    @Transactional
    public boolean hardDeleteBanner(Long id) {
        if (bannerRepository.existsById(id)) {
            bannerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update sort order
    @Transactional
    public void updateSortOrder(Long id, Integer sortOrder) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            banner.setSortOrder(sortOrder);
            bannerRepository.save(banner);
        }
    }

    // Batch update sort order
    @Transactional
    public void batchUpdateSortOrder(List<Long> bannerIds) {
        for (int i = 0; i < bannerIds.size(); i++) {
            updateSortOrder(bannerIds.get(i), i);
        }
    }

    // Get all active banners without sorting
    public List<Banner> getAllActiveBannersUnsorted() {
        return bannerRepository.findByIsActiveTrue();
    }

    // Get all active banners sorted by sort order descending
    public List<Banner> getAllActiveBannersDesc() {
        return bannerRepository.findByIsActiveTrueOrderBySortOrderDesc();
    }
}
