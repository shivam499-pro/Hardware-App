package com.manish.hardware.repository;

import com.manish.hardware.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneAndIsActiveTrue(String phone);
    
    Optional<Customer> findByPhone(String phone);

    boolean existsByPhone(String phone);
}
