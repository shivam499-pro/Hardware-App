package com.manish.hardware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner encodePassword(PasswordEncoder encoder) {
        return args -> {
            System.out.println("ENCODED PASSWORD: " + encoder.encode("admin123"));
        };
    }
}