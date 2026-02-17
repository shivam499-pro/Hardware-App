package com.manish.hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.model.QuoteRequest;
import com.manish.hardware.model.QuoteRequest.QuoteStatus;
import com.manish.hardware.service.QuoteRequestService;
import com.manish.hardware.service.QuoteRequestService.QuoteStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteRequestController.class)
class QuoteRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuoteRequestService quoteRequestService;

    private QuoteRequest testQuote;

    @BeforeEach
    void setUp() {
        testQuote = new QuoteRequest();
        testQuote.setId(1L);
        testQuote.setName("Test User");
        testQuote.setPhone("9876543210");
        testQuote.setProductId(1L);
        testQuote.setQuantity("10 bags");
        testQuote.setLocation("Kathmandu");
        testQuote.setStatus(QuoteStatus.PENDING);
        testQuote.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testSubmitQuoteRequest_Success() throws Exception {
        when(quoteRequestService.createQuoteRequest(any(QuoteRequest.class))).thenReturn(testQuote);

        mockMvc.perform(post("/api/v1/quotes")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testQuote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.phone").value("9876543210"));

        verify(quoteRequestService, times(1)).createQuoteRequest(any(QuoteRequest.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetAllQuoteRequests_Success() throws Exception {
        Page<QuoteRequest> quotePage = new PageImpl<>(Arrays.asList(testQuote));
        when(quoteRequestService.getAllQuoteRequests(any(Pageable.class))).thenReturn(quotePage);

        mockMvc.perform(get("/api/v1/quotes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test User"));

        verify(quoteRequestService, times(1)).getAllQuoteRequests(any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetRecentQuoteRequests_Success() throws Exception {
        when(quoteRequestService.getRecentQuoteRequests()).thenReturn(Arrays.asList(testQuote));

        mockMvc.perform(get("/api/v1/quotes/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User"));

        verify(quoteRequestService, times(1)).getRecentQuoteRequests();
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestById_Success() throws Exception {
        when(quoteRequestService.getQuoteRequestById(1L)).thenReturn(Optional.of(testQuote));

        mockMvc.perform(get("/api/v1/quotes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(quoteRequestService, times(1)).getQuoteRequestById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestsByStatus_Success() throws Exception {
        Page<QuoteRequest> quotePage = new PageImpl<>(Arrays.asList(testQuote));
        when(quoteRequestService.getQuoteRequestsByStatus(any(QuoteStatus.class), any(Pageable.class)))
                .thenReturn(quotePage);

        mockMvc.perform(get("/api/v1/quotes/status/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("pending"));

        verify(quoteRequestService, times(1)).getQuoteRequestsByStatus(any(QuoteStatus.class), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testSearchQuoteRequests_Success() throws Exception {
        Page<QuoteRequest> quotePage = new PageImpl<>(Arrays.asList(testQuote));
        when(quoteRequestService.searchQuoteRequests(anyString(), any(Pageable.class))).thenReturn(quotePage);

        mockMvc.perform(get("/api/v1/quotes/search?q=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test User"));

        verify(quoteRequestService, times(1)).searchQuoteRequests(anyString(), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteStatistics_Success() throws Exception {
        QuoteStatistics stats = new QuoteStatistics(10L, 5L, 3L, 2L);
        when(quoteRequestService.getStatistics()).thenReturn(stats);

        mockMvc.perform(get("/api/v1/quotes/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(10))
                .andExpect(jsonPath("$.pending").value(5));

        verify(quoteRequestService, times(1)).getStatistics();
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateQuoteStatus_Success() throws Exception {
        testQuote.setStatus(QuoteStatus.CONTACTED);
        when(quoteRequestService.updateStatus(1L, QuoteStatus.CONTACTED)).thenReturn(testQuote);

        mockMvc.perform(patch("/api/v1/quotes/1/status?status=CONTACTED")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONTACTED"));

        verify(quoteRequestService, times(1)).updateStatus(1L, QuoteStatus.CONTACTED);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testMarkAsContacted_Success() throws Exception {
        testQuote.setStatus(QuoteStatus.CONTACTED);
        when(quoteRequestService.markAsContacted(1L)).thenReturn(testQuote);

        mockMvc.perform(patch("/api/v1/quotes/1/contacted")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("contacted"));

        verify(quoteRequestService, times(1)).markAsContacted(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testMarkAsCompleted_Success() throws Exception {
        testQuote.setStatus(QuoteStatus.COMPLETED);
        when(quoteRequestService.markAsCompleted(1L)).thenReturn(testQuote);

        mockMvc.perform(patch("/api/v1/quotes/1/completed")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));

        verify(quoteRequestService, times(1)).markAsCompleted(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteQuoteRequest_Success() throws Exception {
        when(quoteRequestService.deleteQuoteRequest(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/quotes/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(quoteRequestService, times(1)).deleteQuoteRequest(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestsByProduct_Success() throws Exception {
        when(quoteRequestService.getQuoteRequestsByProduct(1L)).thenReturn(Arrays.asList(testQuote));

        mockMvc.perform(get("/api/v1/quotes/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1));

        verify(quoteRequestService, times(1)).getQuoteRequestsByProduct(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestsByPhone_Success() throws Exception {
        when(quoteRequestService.getQuoteRequestsByPhone("9876543210")).thenReturn(Arrays.asList(testQuote));

        mockMvc.perform(get("/api/v1/quotes/phone/9876543210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phone").value("9876543210"));

        verify(quoteRequestService, times(1)).getQuoteRequestsByPhone("9876543210");
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestsByLanguageCode_Success() throws Exception {
        when(quoteRequestService.getQuoteRequestsByLanguageCode("en")).thenReturn(Arrays.asList(testQuote));

        mockMvc.perform(get("/api/v1/quotes/language/en"))
                .andExpect(status().isOk());

        verify(quoteRequestService, times(1)).getQuoteRequestsByLanguageCode("en");
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestsByStatuses_Success() throws Exception {
        List<QuoteStatus> statuses = Arrays.asList(QuoteStatus.PENDING, QuoteStatus.CONTACTED);
        when(quoteRequestService.getQuoteRequestsByStatusIn(anyList())).thenReturn(Arrays.asList(testQuote));

        mockMvc.perform(post("/api/v1/quotes/statuses")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statuses)))
                .andExpect(status().isOk());

        verify(quoteRequestService, times(1)).getQuoteRequestsByStatusIn(anyList());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetQuoteRequestById_WithAnyLong() throws Exception {
        when(quoteRequestService.getQuoteRequestById(anyLong())).thenReturn(Optional.of(testQuote));

        mockMvc.perform(get("/api/v1/quotes/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(quoteRequestService, times(1)).getQuoteRequestById(anyLong());
    }
}
