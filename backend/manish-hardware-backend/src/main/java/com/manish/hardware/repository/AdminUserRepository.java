package com.manish.hardware.repository;

import com.manish.hardware.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    // Find by username
    Optional<AdminUser> findByUsername(String username);

    // Find by email
    Optional<AdminUser> findByEmail(String email);

    // Check if username exists
    boolean existsByUsername(String username);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find by username and active status
    Optional<AdminUser> findByUsernameAndIsActiveTrue(String username);
}
