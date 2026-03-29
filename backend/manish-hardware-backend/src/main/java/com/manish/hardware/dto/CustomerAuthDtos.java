package com.manish.hardware.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CustomerAuthDtos {

    @Data
    public static class LoginRequest {
        @NotBlank(message = "Phone number is required")
        private String phone;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private final String tokenType = "Bearer";
        private long expiresIn;
        private String phone;
        private String fullName;
        private String location;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Phone number is required")
        private String phone;

        @NotBlank(message = "Password is required")
        private String password;

        @NotBlank(message = "Full name is required")
        private String fullName;

        private String location;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerProfile {
        private Long id;
        private String phone;
        private String fullName;
        private String location;
        private Boolean isActive;
    }
}
