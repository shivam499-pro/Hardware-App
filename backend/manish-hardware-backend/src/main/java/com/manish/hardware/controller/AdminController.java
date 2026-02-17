package com.manish.hardware.controller;

import com.manish.hardware.service.CategoryService;
import com.manish.hardware.service.ProductService;
import com.manish.hardware.service.QuoteRequestService;
import com.manish.hardware.service.BannerService;
import com.manish.hardware.service.QuoteRequestService.QuoteStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private QuoteRequestService quoteRequestService;

    @Autowired
    private BannerService bannerService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardStats() {
        Map<String, Object> dashboard = new HashMap<>();

        // Get category count
        List<?> categories = categoryService.getAllActiveCategories();
        dashboard.put("totalCategories", categories.size());

        // Get product count
        long productCount = productService.getActiveProductCount();
        dashboard.put("totalProducts", productCount);

        // Get quote statistics
        QuoteStatistics quoteStats = quoteRequestService.getStatistics();
        dashboard.put("quoteStats", quoteStats);

        // Get recent quotes
        List<?> recentQuotes = quoteRequestService.getRecentQuoteRequests();
        dashboard.put("recentQuotes", recentQuotes);

        // Get banner count
        List<?> banners = bannerService.getAllActiveBanners();
        dashboard.put("totalBanners", banners.size());

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = new HashMap<>();

        // Categories
        stats.put("categories", categoryService.getAllActiveCategories().size());

        // Products
        stats.put("products", productService.getActiveProductCount());

        // Quotes
        QuoteStatistics quoteStats = quoteRequestService.getStatistics();
        stats.put("totalQuotes", quoteStats.getTotal());
        stats.put("pendingQuotes", quoteStats.getPending());
        stats.put("contactedQuotes", quoteStats.getContacted());
        stats.put("completedQuotes", quoteStats.getCompleted());

        // Banners
        stats.put("banners", bannerService.getAllActiveBanners().size());

        return ResponseEntity.ok(stats);
    }
}
