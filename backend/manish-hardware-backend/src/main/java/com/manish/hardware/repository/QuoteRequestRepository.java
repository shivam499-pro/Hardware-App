package com.manish.hardware.repository;

import com.manish.hardware.model.QuoteRequest;
import com.manish.hardware.model.QuoteRequest.QuoteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {

    // Find all by status
    List<QuoteRequest> findByStatus(QuoteStatus status);

    // Find all by status with pagination
    Page<QuoteRequest> findByStatus(QuoteStatus status, Pageable pageable);

    // Find all by status in
    List<QuoteRequest> findByStatusIn(List<QuoteStatus> statuses);

    // Find all by product id
    List<QuoteRequest> findByProductId(Long productId);

    // Find all by phone number
    List<QuoteRequest> findByPhone(String phone);

    // Find all by language code
    List<QuoteRequest> findByLanguageCode(String languageCode);

    // Find all created after a date
    List<QuoteRequest> findByCreatedAtAfter(LocalDateTime date);

    // Find all created between dates
    List<QuoteRequest> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find all created between dates with pagination
    Page<QuoteRequest> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Count by status
    long countByStatus(QuoteStatus status);

    // Count all quotes
    long count();

    // Find recent quotes
    List<QuoteRequest> findTop10ByOrderByCreatedAtDesc();

    // Search by name or phone
    @Query("SELECT q FROM QuoteRequest q WHERE LOWER(q.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR q.phone LIKE CONCAT('%', :searchTerm, '%')")
    Page<QuoteRequest> searchByNameOrPhone(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Find by status and date range
    @Query("SELECT q FROM QuoteRequest q WHERE q.status = :status AND q.createdAt BETWEEN :startDate AND :endDate")
    List<QuoteRequest> findByStatusAndDateRange(
            @Param("status") QuoteStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
