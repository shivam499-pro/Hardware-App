package com.manish.hardware.controller;

import com.manish.hardware.dto.CustomerAuthDtos;
import com.manish.hardware.model.Customer;
import com.manish.hardware.service.CustomerAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer-auth")
@CrossOrigin(origins = "*")
public class CustomerAuthController {

    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody CustomerAuthDtos.LoginRequest request) {
        try {
            CustomerAuthDtos.LoginResponse response = customerAuthService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid phone number or password");
            return ResponseEntity.status(401).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerAuthDtos.RegisterRequest request) {
        try {
            Customer customer = customerAuthService.register(request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Customer registered successfully");
            response.put("phone", customer.getPhone());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
