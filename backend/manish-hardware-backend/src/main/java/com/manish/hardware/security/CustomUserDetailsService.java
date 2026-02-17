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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Use role from database or default to ADMIN
        String role = adminUser.getRole() != null ? adminUser.getRole() : "ADMIN";

        return new User(
                adminUser.getUsername(),
                adminUser.getPassword(),
                adminUser.getIsActive(),
                true,
                true,
                true,
                Collections.singletonList(
                    new SimpleGrantedAuthority(
                        role.startsWith("ROLE_") ? role : "ROLE_" + role)));
    }
}
