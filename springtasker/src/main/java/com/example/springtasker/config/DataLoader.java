package com.example.springtasker.config;

import com.example.springtasker.model.Role;
import com.example.springtasker.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class DataLoader implements CommandLineRunner {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final RoleRepository roles;
    public DataLoader(RoleRepository roles) { this.roles = roles; }

    @Override
    public void run(String... args) {
        if (roles.count() == 0) {
            roles.save(new Role("ROLE_USER"));
            roles.save(new Role("ROLE_ADMIN"));
        }
    }

    public static final String ROLE_USER = "ROLE_USER";
}
