package com.manish.hardware.controller;

import com.manish.hardware.model.QuoteRequest;
import com.manish.hardware.model.QuoteRequest.QuoteStatus;
import com.manish.hardware.service.QuoteRequestService;
import com.manish.hardware.service.QuoteRequestService.QuoteStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/quotes")
@CrossOrigin(origins = "*")
public class QuoteRequestController {

    @Autowired
    private QuoteRequestService quoteRequestService;

    // Submit a new quote request (public endpoint)
    @PostMapping
    public ResponseEntity<QuoteRequest> submitQuoteRequest(@RequestBody QuoteRequest quoteRequest) {
        QuoteRequest createdQuote = quoteRequestService.createQuoteRequest(quoteRequest);
        return ResponseEntity.ok(createdQuote);
    }

    // Get all quote requests with pagination (admin endpoint)
    @GetMapping
    public ResponseEntity<Page<QuoteRequest>> getAllQuoteRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<QuoteRequest> quotes = quoteRequestService.getAllQuoteRequests(pageable);
        return ResponseEntity.ok(quotes);
    }

    // Get recent quote requests (admin endpoint)
    @GetMapping("/recent")
    public ResponseEntity<List<QuoteRequest>> getRecentQuoteRequests() {
        List<QuoteRequest> quotes = quoteRequestService.getRecentQuoteRequests();
        return ResponseEntity.ok(quotes);
    }

    // Get quote request by ID (admin endpoint)
    @GetMapping("/{id}")
    public ResponseEntity<QuoteRequest> getQuoteRequestById(@PathVariable Long id) {
        Optional<QuoteRequest> quote = quoteRequestService.getQuoteRequestById(id);
        return quote.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get quote requests by status (admin endpoint)
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<QuoteRequest>> getQuoteRequestsByStatus(
            @PathVariable QuoteStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByStatus(status, pageable);
        return ResponseEntity.ok(quotes);
    }

    // Search quote requests (admin endpoint)
    @GetMapping("/search")
    public ResponseEntity<Page<QuoteRequest>> searchQuoteRequests(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<QuoteRequest> quotes = quoteRequestService.searchQuoteRequests(q, pageable);
        return ResponseEntity.ok(quotes);
    }

    // Get quote requests by date range (admin endpoint)
    @GetMapping("/date-range")
    public ResponseEntity<Page<QuoteRequest>> getQuoteRequestsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByDateRange(start, end, pageable);
        return ResponseEntity.ok(quotes);
    }

    // Get quote statistics (admin endpoint)
    @GetMapping("/statistics")
    public ResponseEntity<QuoteStatistics> getQuoteStatistics() {
        QuoteStatistics statistics = quoteRequestService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    // Update quote request (admin endpoint)
    @PutMapping("/{id}")
    public ResponseEntity<QuoteRequest> updateQuoteRequest(@PathVariable Long id,
            @RequestBody QuoteRequest quoteRequest) {
        QuoteRequest updatedQuote = quoteRequestService.updateQuoteRequest(id, quoteRequest);
        if (updatedQuote != null) {
            return ResponseEntity.ok(updatedQuote);
        }
        return ResponseEntity.notFound().build();
    }

    // Update quote request status (admin endpoint)
    @PatchMapping("/{id}/status")
    public ResponseEntity<QuoteRequest> updateQuoteStatus(@PathVariable Long id, @RequestParam QuoteStatus status) {
        QuoteRequest updatedQuote = quoteRequestService.updateStatus(id, status);
        if (updatedQuote != null) {
            return ResponseEntity.ok(updatedQuote);
        }
        return ResponseEntity.notFound().build();
    }

    // Mark as contacted (admin endpoint)
    @PatchMapping("/{id}/contacted")
    public ResponseEntity<QuoteRequest> markAsContacted(@PathVariable Long id) {
        QuoteRequest updatedQuote = quoteRequestService.markAsContacted(id);
        if (updatedQuote != null) {
            return ResponseEntity.ok(updatedQuote);
        }
        return ResponseEntity.notFound().build();
    }

    // Mark as completed (admin endpoint)
    @PatchMapping("/{id}/completed")
    public ResponseEntity<QuoteRequest> markAsCompleted(@PathVariable Long id) {
        QuoteRequest updatedQuote = quoteRequestService.markAsCompleted(id);
        if (updatedQuote != null) {
            return ResponseEntity.ok(updatedQuote);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete quote request (admin endpoint)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteRequest(@PathVariable Long id) {
        boolean deleted = quoteRequestService.deleteQuoteRequest(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get quote requests by product (admin endpoint)
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsByProduct(@PathVariable Long productId) {
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByProduct(productId);
        return ResponseEntity.ok(quotes);
    }

    // Get quote requests by phone (admin endpoint)
    @GetMapping("/phone/{phone}")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsByPhone(@PathVariable String phone) {
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByPhone(phone);
        return ResponseEntity.ok(quotes);
    }

    // Get quote requests by language code (admin endpoint)
    @GetMapping("/language/{languageCode}")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsByLanguageCode(@PathVariable String languageCode) {
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByLanguageCode(languageCode);
        return ResponseEntity.ok(quotes);
    }

    // Get quote requests created after a date (admin endpoint)
    @GetMapping("/after/{date}")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsCreatedAfter(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsCreatedAfter(dateTime);
        return ResponseEntity.ok(quotes);
    }

    // Get quote requests by multiple statuses (admin endpoint)
    @PostMapping("/statuses")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsByStatuses(@RequestBody List<QuoteStatus> statuses) {
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByStatusIn(statuses);
        return ResponseEntity.ok(quotes);
    }
}
