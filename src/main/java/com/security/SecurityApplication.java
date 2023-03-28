package com.security;

import com.security.model.User;
import com.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository) {
        return (arg) -> {
            List<User> users = userRepository.findAll();
            users.forEach(user -> {
                log.info(user.toString());
            });
        };
    }

}
