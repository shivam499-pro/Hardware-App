package com.manish.hardware.service;

import com.manish.hardware.dto.AuthDtos;
import com.manish.hardware.model.AdminUser;
import com.manish.hardware.repository.AdminUserRepository;
import com.manish.hardware.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Login
    public AuthDtos.LoginResponse login(AuthDtos.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        AdminUser user = adminUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        adminUserRepository.save(user);

        return new AuthDtos.LoginResponse(
                token,
                tokenProvider.getExpirationTime(),
                user.getUsername(),
                user.getFullName());
    }

    // Register new admin user
    @Transactional
    public AdminUser register(AuthDtos.RegisterRequest registerRequest) {
        if (adminUserRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (registerRequest.getEmail() != null && adminUserRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        AdminUser user = new AdminUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setIsActive(true);
        user.setRole("ADMIN"); // Set default role

        return adminUserRepository.save(user);
    }

    // Get current user
    public Optional<AdminUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return adminUserRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    // Get user profile
    public AuthDtos.UserProfile getCurrentUserProfile() {
        return getCurrentUser()
                .map(user -> new AuthDtos.UserProfile(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getIsActive()))
                .orElse(null);
    }

    // Change password
    @Transactional
    public boolean changePassword(AuthDtos.ChangePasswordRequest request) {
        Optional<AdminUser> optionalUser = getCurrentUser();
        if (optionalUser.isPresent()) {
            AdminUser user = optionalUser.get();
            if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                adminUserRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // Update profile
    @Transactional
    public AdminUser updateProfile(String fullName, String email) {
        Optional<AdminUser> optionalUser = getCurrentUser();
        if (optionalUser.isPresent()) {
            AdminUser user = optionalUser.get();
            if (email != null && !email.equals(user.getEmail()) && adminUserRepository.existsByEmail(email)) {
                throw new RuntimeException("Email is already in use!");
            }
            user.setFullName(fullName);
            user.setEmail(email);
            return adminUserRepository.save(user);
        }
        return null;
    }

    // Get all admin users (for super admin)
    public Iterable<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    // Deactivate user
    @Transactional
    public boolean deactivateUser(Long userId) {
        Optional<AdminUser> optionalUser = adminUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AdminUser user = optionalUser.get();
            user.setIsActive(false);
            adminUserRepository.save(user);
            return true;
        }
        return false;
    }

    // Activate user
    @Transactional
    public boolean activateUser(Long userId) {
        Optional<AdminUser> optionalUser = adminUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AdminUser user = optionalUser.get();
            user.setIsActive(true);
            adminUserRepository.save(user);
            return true;
        }
        return false;
    }

    // Reset password (for admin)
    @Transactional
    public boolean resetPassword(Long userId, String newPassword) {
        Optional<AdminUser> optionalUser = adminUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AdminUser user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            adminUserRepository.save(user);
            return true;
        }
        return false;
    }

    // Find user by email
    public Optional<AdminUser> getUserByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }
}
