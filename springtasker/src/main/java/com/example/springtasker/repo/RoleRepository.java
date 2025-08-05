package com.example.springtasker.repo;

import com.example.springtasker.model.Role;
import com.example.springtasker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
