package com.manish.hardware.service;

import com.manish.hardware.dto.AuthDtos;
import com.manish.hardware.model.AdminUser;
import com.manish.hardware.repository.AdminUserRepository;
import com.manish.hardware.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private AdminUser testUser;
    private AuthDtos.LoginRequest loginRequest;
    private AuthDtos.RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUser = new AdminUser();
        testUser.setId(1L);
        testUser.setUsername("admin");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("admin@test.com");
        testUser.setFullName("Admin User");
        testUser.setIsActive(true);
        testUser.setRole("ADMIN");

        loginRequest = new AuthDtos.LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password123");

        registerRequest = new AuthDtos.RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("newuser@test.com");
        registerRequest.setFullName("New User");
    }

    @Test
    void testLogin_Success() {
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtTokenProvider.generateToken(any())).thenReturn("test-token");

        AuthDtos.LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("test-token", response.getAccessToken());
        assertEquals("admin", response.getUsername());

        verify(adminUserRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
    }

    @Test
    void testLogin_InvalidPassword() {
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));

        verify(adminUserRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
    }

    @Test
    void testLogin_UserNotFound() {
        when(adminUserRepository.findByUsername("admin")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));

        verify(adminUserRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testRegister_Success() {
        when(adminUserRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(testUser);

        AdminUser result = authService.register(registerRequest);

        assertNotNull(result);

        verify(adminUserRepository, times(1)).existsByUsername("newuser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(adminUserRepository, times(1)).save(any(AdminUser.class));
    }

    @Test
    void testRegister_UsernameExists() {
        when(adminUserRepository.existsByUsername("newuser")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));

        verify(adminUserRepository, times(1)).existsByUsername("newuser");
    }

    @Test
    void testGetAllAdminUsers() {
        when(adminUserRepository.findAll()).thenReturn(Arrays.asList(testUser));

        Iterable<AdminUser> users = authService.getAllAdminUsers();

        assertNotNull(users);

        verify(adminUserRepository, times(1)).findAll();
    }

    @Test
    void testDeactivateUser_Success() {
        when(adminUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(testUser);

        boolean result = authService.deactivateUser(1L);

        assertTrue(result);

        verify(adminUserRepository, times(1)).findById(1L);
        verify(adminUserRepository, times(1)).save(any(AdminUser.class));
    }

    @Test
    void testDeactivateUser_NotFound() {
        when(adminUserRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = authService.deactivateUser(1L);

        assertFalse(result);

        verify(adminUserRepository, times(1)).findById(1L);
    }

    @Test
    void testActivateUser_Success() {
        testUser.setIsActive(false);
        when(adminUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(testUser);

        boolean result = authService.activateUser(1L);

        assertTrue(result);

        verify(adminUserRepository, times(1)).findById(1L);
        verify(adminUserRepository, times(1)).save(any(AdminUser.class));
    }

    @Test
    void testResetPassword_Success() {
        when(adminUserRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(testUser);

        boolean result = authService.resetPassword(1L, "newPassword");

        assertTrue(result);

        verify(adminUserRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(adminUserRepository, times(1)).save(any(AdminUser.class));
    }

    @Test
    void testGetUserByEmail_Success() {
        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(testUser));

        Optional<AdminUser> result = authService.getUserByEmail("admin@test.com");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());

        verify(adminUserRepository, times(1)).findByEmail("admin@test.com");
    }
}
