package com.manish.hardware.security;

import com.manish.hardware.model.AdminUser;
import com.manish.hardware.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private com.manish.hardware.repository.CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try Admin first (they login with username)
        java.util.Optional<AdminUser> optionalAdmin = adminUserRepository.findByUsernameAndIsActiveTrue(username);
        if (optionalAdmin.isPresent()) {
            AdminUser adminUser = optionalAdmin.get();
            String role = adminUser.getRole() != null ? adminUser.getRole() : "ADMIN";
            return new User(
                    adminUser.getUsername(),
                    adminUser.getPassword(),
                    adminUser.getIsActive(),
                    true, true, true,
                    Collections.singletonList(new SimpleGrantedAuthority(
                            role.startsWith("ROLE_") ? role : "ROLE_" + role))
            );
        }

        // Try Customer next (they login with phone number)
        java.util.Optional<com.manish.hardware.model.Customer> optionalCustomer = customerRepository.findByPhoneAndIsActiveTrue(username);
        if (optionalCustomer.isPresent()) {
            com.manish.hardware.model.Customer customer = optionalCustomer.get();
            return new User(
                    customer.getPhone(),
                    customer.getPassword(),
                    customer.getIsActive(),
                    true, true, true,
                    Collections.singletonList(new SimpleGrantedAuthority(customer.getRole()))
            );
        }

        throw new UsernameNotFoundException("User/Customer not found with identifier: " + username);
    }
}
