package com.manish.hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.model.Banner;
import com.manish.hardware.service.BannerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BannerController.class)
class BannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BannerService bannerService;

    private Banner testBanner;

    @BeforeEach
    void setUp() {
        testBanner = new Banner();
        testBanner.setId(1L);
        testBanner.setTitle("Test Banner");
        testBanner.setImageUrl("http://example.com/image.jpg");
        testBanner.setLinkUrl("http://example.com");
        testBanner.setSortOrder(1);
        testBanner.setIsActive(true);
    }

    @Test
    void testGetActiveBanners_Success() throws Exception {
        when(bannerService.getAllActiveBanners()).thenReturn(Arrays.asList(testBanner));

        mockMvc.perform(get("/api/v1/banners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Banner"));

        verify(bannerService, times(1)).getAllActiveBanners();
    }

    @Test
    void testGetAllBanners_Success() throws Exception {
        when(bannerService.getAllBanners()).thenReturn(Arrays.asList(testBanner));

        mockMvc.perform(get("/api/v1/banners/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Banner"));

        verify(bannerService, times(1)).getAllBanners();
    }

    @Test
    void testGetBannerById_Success() throws Exception {
        when(bannerService.getBannerById(1L)).thenReturn(Optional.of(testBanner));

        mockMvc.perform(get("/api/v1/banners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Banner"));

        verify(bannerService, times(1)).getBannerById(1L);
    }

    @Test
    void testGetBannerById_NotFound() throws Exception {
        when(bannerService.getBannerById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/banners/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateBanner_Success() throws Exception {
        when(bannerService.createBanner(any(Banner.class))).thenReturn(testBanner);

        mockMvc.perform(post("/api/v1/banners")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBanner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Banner"));

        verify(bannerService, times(1)).createBanner(any(Banner.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateBanner_Success() throws Exception {
        when(bannerService.updateBanner(anyLong(), any(Banner.class))).thenReturn(testBanner);

        mockMvc.perform(put("/api/v1/banners/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBanner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Banner"));

        verify(bannerService, times(1)).updateBanner(anyLong(), any(Banner.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteBanner_Success() throws Exception {
        when(bannerService.deleteBanner(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/banners/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(bannerService, times(1)).deleteBanner(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testHardDeleteBanner_Success() throws Exception {
        when(bannerService.hardDeleteBanner(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/banners/1/hard")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(bannerService, times(1)).hardDeleteBanner(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateSortOrder_Success() throws Exception {
        doNothing().when(bannerService).updateSortOrder(anyLong(), anyInt());

        mockMvc.perform(put("/api/v1/banners/1/sort?sortOrder=5")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(bannerService, times(1)).updateSortOrder(1L, 5);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testBatchUpdateSortOrder_Success() throws Exception {
        doNothing().when(bannerService).batchUpdateSortOrder(anyList());

        mockMvc.perform(put("/api/v1/banners/sort-order")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2, 3]"))
                .andExpect(status().isOk());

        verify(bannerService, times(1)).batchUpdateSortOrder(anyList());
    }

    @Test
    void testGetActiveBannersUnsorted_Success() throws Exception {
        when(bannerService.getAllActiveBannersUnsorted()).thenReturn(Arrays.asList(testBanner));

        mockMvc.perform(get("/api/v1/banners/unsorted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Banner"));

        verify(bannerService, times(1)).getAllActiveBannersUnsorted();
    }

    @Test
    void testGetActiveBannersDesc_Success() throws Exception {
        when(bannerService.getAllActiveBannersDesc()).thenReturn(Arrays.asList(testBanner));

        mockMvc.perform(get("/api/v1/banners/desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Banner"));

        verify(bannerService, times(1)).getAllActiveBannersDesc();
    }
}
