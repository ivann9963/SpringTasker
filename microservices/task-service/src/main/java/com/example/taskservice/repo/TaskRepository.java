package com.example.taskservice.repo;

import com.example.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);
    Optional<Task> findByIdAndUsername(Long id, String username);
} 