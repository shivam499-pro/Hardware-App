package com.manish.hardware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.dto.AuthDtos;
import com.manish.hardware.model.AdminUser;
import com.manish.hardware.service.AuthService;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private AdminUser testUser;
    private AuthDtos.LoginRequest loginRequest;
    private AuthDtos.RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUser = new AdminUser();
        testUser.setId(1L);
        testUser.setUsername("admin");
        testUser.setEmail("admin@test.com");
        testUser.setFullName("Admin User");
        testUser.setIsActive(true);
        testUser.setRole("ADMIN");

        loginRequest = new AuthDtos.LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");

        registerRequest = new AuthDtos.RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("newuser@test.com");
        registerRequest.setFullName("New User");
    }

    @Test
    void testLogin_Success() throws Exception {
        AuthDtos.LoginResponse response = new AuthDtos.LoginResponse();
        response.setAccessToken("test-token");
        response.setUsername("admin");

        when(authService.login(any(AuthDtos.LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("test-token"))
                .andExpect(jsonPath("$.username").value("admin"));

        verify(authService, times(1)).login(any(AuthDtos.LoginRequest.class));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        when(authService.login(any(AuthDtos.LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }

    @Test
    void testRegister_Success() throws Exception {
        when(authService.register(any(AuthDtos.RegisterRequest.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/v1/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.username").value("admin"));

        verify(authService, times(1)).register(any(AuthDtos.RegisterRequest.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetCurrentUser_Success() throws Exception {
        AuthDtos.UserProfile profile = new AuthDtos.UserProfile();
        profile.setUsername("admin");
        profile.setEmail("admin@test.com");
        profile.setFullName("Admin User");

        when(authService.getCurrentUserProfile()).thenReturn(profile);

        mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetAllAdminUsers_Success() throws Exception {
        when(authService.getAllAdminUsers()).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/api/v1/auth/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeactivateUser_Success() throws Exception {
        when(authService.deactivateUser(1L)).thenReturn(true);

        mockMvc.perform(put("/api/v1/auth/users/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deactivated successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testActivateUser_Success() throws Exception {
        when(authService.activateUser(1L)).thenReturn(true);

        mockMvc.perform(put("/api/v1/auth/users/1/activate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User activated successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetUserByEmail_Success() throws Exception {
        when(authService.getUserByEmail("admin@test.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/v1/auth/users/by-email/admin@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetUserByEmail_NotFound() throws Exception {
        when(authService.getUserByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/auth/users/by-email/nonexistent@test.com"))
                .andExpect(status().isNotFound());
    }
}
