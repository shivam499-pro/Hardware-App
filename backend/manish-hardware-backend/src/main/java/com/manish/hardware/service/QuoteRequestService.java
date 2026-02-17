package com.manish.hardware.service;

import com.manish.hardware.model.QuoteRequest;
import com.manish.hardware.model.QuoteRequest.QuoteStatus;
import com.manish.hardware.repository.QuoteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteRequestService {

    @Autowired
    private QuoteRequestRepository quoteRequestRepository;

    // Get all quote requests
    public List<QuoteRequest> getAllQuoteRequests() {
        return quoteRequestRepository.findAll();
    }

    // Get all quote requests with pagination
    public Page<QuoteRequest> getAllQuoteRequests(Pageable pageable) {
        return quoteRequestRepository.findAll(pageable);
    }

    // Get quote request by ID
    public Optional<QuoteRequest> getQuoteRequestById(Long id) {
        return quoteRequestRepository.findById(id);
    }

    // Get quote requests by status
    public List<QuoteRequest> getQuoteRequestsByStatus(QuoteStatus status) {
        return quoteRequestRepository.findByStatus(status);
    }

    // Get quote requests by status with pagination
    public Page<QuoteRequest> getQuoteRequestsByStatus(QuoteStatus status, Pageable pageable) {
        return quoteRequestRepository.findByStatus(status, pageable);
    }

    // Get recent quote requests
    public List<QuoteRequest> getRecentQuoteRequests() {
        return quoteRequestRepository.findTop10ByOrderByCreatedAtDesc();
    }

    // Get quote requests by date range
    public List<QuoteRequest> getQuoteRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return quoteRequestRepository.findByCreatedAtBetween(startDate, endDate);
    }

    // Get quote requests by date range with pagination
    public Page<QuoteRequest> getQuoteRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        return quoteRequestRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    // Search quote requests
    public Page<QuoteRequest> searchQuoteRequests(String searchTerm, Pageable pageable) {
        return quoteRequestRepository.searchByNameOrPhone(searchTerm, pageable);
    }

    // Create quote request
    @Transactional
    public QuoteRequest createQuoteRequest(QuoteRequest quoteRequest) {
        quoteRequest.setStatus(QuoteStatus.PENDING);
        return quoteRequestRepository.save(quoteRequest);
    }

    // Update quote request
    @Transactional
    public QuoteRequest updateQuoteRequest(Long id, QuoteRequest quoteRequestDetails) {
        Optional<QuoteRequest> optionalQuoteRequest = quoteRequestRepository.findById(id);
        if (optionalQuoteRequest.isPresent()) {
            QuoteRequest quoteRequest = optionalQuoteRequest.get();
            quoteRequest.setName(quoteRequestDetails.getName());
            quoteRequest.setPhone(quoteRequestDetails.getPhone());
            quoteRequest.setProductId(quoteRequestDetails.getProductId());
            quoteRequest.setQuantity(quoteRequestDetails.getQuantity());
            quoteRequest.setLocation(quoteRequestDetails.getLocation());
            quoteRequest.setLanguageCode(quoteRequestDetails.getLanguageCode());
            return quoteRequestRepository.save(quoteRequest);
        }
        return null;
    }

    // Update quote request status
    @Transactional
    public QuoteRequest updateStatus(Long id, QuoteStatus status) {
        Optional<QuoteRequest> optionalQuoteRequest = quoteRequestRepository.findById(id);
        if (optionalQuoteRequest.isPresent()) {
            QuoteRequest quoteRequest = optionalQuoteRequest.get();
            quoteRequest.setStatus(status);
            return quoteRequestRepository.save(quoteRequest);
        }
        return null;
    }

    // Mark as contacted
    @Transactional
    public QuoteRequest markAsContacted(Long id) {
        return updateStatus(id, QuoteStatus.CONTACTED);
    }

    // Mark as completed
    @Transactional
    public QuoteRequest markAsCompleted(Long id) {
        return updateStatus(id, QuoteStatus.COMPLETED);
    }

    // Delete quote request
    @Transactional
    public boolean deleteQuoteRequest(Long id) {
        if (quoteRequestRepository.existsById(id)) {
            quoteRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get quote requests by product
    public List<QuoteRequest> getQuoteRequestsByProduct(Long productId) {
        return quoteRequestRepository.findByProductId(productId);
    }

    // Get quote requests by phone
    public List<QuoteRequest> getQuoteRequestsByPhone(String phone) {
        return quoteRequestRepository.findByPhone(phone);
    }

    // Get quote requests by language code
    public List<QuoteRequest> getQuoteRequestsByLanguageCode(String languageCode) {
        return quoteRequestRepository.findByLanguageCode(languageCode);
    }

    // Get quote requests created after a date
    public List<QuoteRequest> getQuoteRequestsCreatedAfter(LocalDateTime date) {
        return quoteRequestRepository.findByCreatedAtAfter(date);
    }

    // Get quote requests by multiple statuses
    public List<QuoteRequest> getQuoteRequestsByStatusIn(List<QuoteStatus> statuses) {
        return quoteRequestRepository.findByStatusIn(statuses);
    }

    // Get quote requests by status and date range
    public List<QuoteRequest> getQuoteRequestsByStatusAndDateRange(QuoteStatus status, LocalDateTime startDate,
            LocalDateTime endDate) {
        return quoteRequestRepository.findByStatusAndDateRange(status, startDate, endDate);
    }

    // Count by status
    public long countByStatus(QuoteStatus status) {
        return quoteRequestRepository.countByStatus(status);
    }

    // Get statistics
    public QuoteStatistics getStatistics() {
        long pending = countByStatus(QuoteStatus.PENDING);
        long contacted = countByStatus(QuoteStatus.CONTACTED);
        long completed = countByStatus(QuoteStatus.COMPLETED);
        long total = quoteRequestRepository.count();

        return new QuoteStatistics(total, pending, contacted, completed);
    }

    // Statistics class
    public static class QuoteStatistics {
        private final long total;
        private final long pending;
        private final long contacted;
        private final long completed;

        public QuoteStatistics(long total, long pending, long contacted, long completed) {
            this.total = total;
            this.pending = pending;
            this.contacted = contacted;
            this.completed = completed;
        }

        public long getTotal() {
            return total;
        }

        public long getPending() {
            return pending;
        }

        public long getContacted() {
            return contacted;
        }

        public long getCompleted() {
            return completed;
        }
    }
}
