package com.manish.hardware.service;

import com.manish.hardware.dto.CustomerAuthDtos;
import com.manish.hardware.model.Customer;
import com.manish.hardware.repository.CustomerRepository;
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

@Service
public class CustomerAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public CustomerAuthDtos.LoginResponse login(CustomerAuthDtos.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        Customer customer = customerRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setLastLogin(LocalDateTime.now());
        customerRepository.save(customer);

        return new CustomerAuthDtos.LoginResponse(
                token,
                tokenProvider.getExpirationTime(),
                customer.getPhone(),
                customer.getFullName(),
                customer.getLocation()
        );
    }

    @Transactional
    public Customer register(CustomerAuthDtos.RegisterRequest request) {
        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number is already registered!");
        }

        Customer customer = new Customer();
        customer.setPhone(request.getPhone());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setFullName(request.getFullName());
        customer.setLocation(request.getLocation());
        customer.setIsActive(true);
        customer.setRole("ROLE_CUSTOMER");

        return customerRepository.save(customer);
    }
}
