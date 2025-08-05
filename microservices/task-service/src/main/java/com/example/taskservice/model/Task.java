package com.example.taskservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title cannot be empty")
    @Size(min = 1, max = 255, message = "Task title must be between 1 and 255 characters")
    private String title;
    
    private boolean completed;

    // In microservices, we store the username instead of a User entity
    // This avoids cross-service database dependencies
    @NotNull(message = "Task must belong to a user")
    private String username; // Store username instead of User entity
} 