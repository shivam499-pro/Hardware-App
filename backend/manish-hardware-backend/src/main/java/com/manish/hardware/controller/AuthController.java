package com.manish.hardware.controller;

import com.manish.hardware.dto.AuthDtos;
import com.manish.hardware.model.AdminUser;
import com.manish.hardware.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDtos.LoginRequest loginRequest) {
        try {
            AuthDtos.LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(error);
        }
    }

    // Register endpoint (for creating new admin users)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDtos.RegisterRequest registerRequest) {
        try {
            AdminUser user = authService.register(registerRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get current user profile
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        AuthDtos.UserProfile profile = authService.getCurrentUserProfile();
        if (profile != null) {
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(401).build();
    }

    // Change password
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody AuthDtos.ChangePasswordRequest request) {
        boolean success = authService.changePassword(request);
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully");
            return ResponseEntity.ok(response);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Failed to change password. Please check your current password.");
        return ResponseEntity.badRequest().body(error);
    }

    // Update profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> request) {
        try {
            String fullName = request.get("fullName");
            String email = request.get("email");
            AdminUser user = authService.updateProfile(fullName, email);
            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Profile updated successfully");
                response.put("fullName", user.getFullName());
                response.put("email", user.getEmail());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(401).build();
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Admin management endpoints

    // Get all admin users
    @GetMapping("/users")
    public ResponseEntity<Iterable<AdminUser>> getAllAdminUsers() {
        return ResponseEntity.ok(authService.getAllAdminUsers());
    }

    // Deactivate user
    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        boolean success = authService.deactivateUser(id);
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deactivated successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Activate user
    @PutMapping("/users/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        boolean success = authService.activateUser(id);
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User activated successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Reset password for user
    @PutMapping("/users/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        if (newPassword == null || newPassword.length() < 6) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Password must be at least 6 characters");
            return ResponseEntity.badRequest().body(error);
        }

        boolean success = authService.resetPassword(id, newPassword);
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Get user by email (admin endpoint)
    @GetMapping("/users/by-email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return authService.getUserByEmail(email)
                .map(user -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", user.getId());
                    response.put("username", user.getUsername());
                    response.put("email", user.getEmail());
                    response.put("fullName", user.getFullName());
                    response.put("isActive", user.getIsActive());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
