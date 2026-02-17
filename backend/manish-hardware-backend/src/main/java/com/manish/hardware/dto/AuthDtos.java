package com.manish.hardware.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

    // Login Request DTO
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Login Response DTO
    public static class LoginResponse {
        private String accessToken;
        private String tokenType = "Bearer";
        private long expiresIn;
        private String username;
        private String fullName;

        public LoginResponse() {
        }

        public LoginResponse(String accessToken, long expiresIn, String username, String fullName) {
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
            this.username = username;
            this.fullName = fullName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    // Register Request DTO
    public static class RegisterRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        private String email;

        private String fullName;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    // Change Password Request DTO
    public static class ChangePasswordRequest {
        @NotBlank
        private String currentPassword;

        @NotBlank
        private String newPassword;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    // User Profile DTO
    public static class UserProfile {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private Boolean isActive;

        public UserProfile() {
        }

        public UserProfile(Long id, String username, String email, String fullName, Boolean isActive) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.isActive = isActive;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }
}
