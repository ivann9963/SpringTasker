package com.example.taskservice.controller;

import com.example.taskservice.client.UserServiceClient;
import com.example.taskservice.model.Task;
import com.example.taskservice.repo.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;

    public TaskController(TaskRepository taskRepository, UserServiceClient userServiceClient) {
        this.taskRepository = taskRepository;
        this.userServiceClient = userServiceClient;
    }

    // Get all tasks for the authenticated user
    @GetMapping
    public Mono<ResponseEntity<List<Task>>> getAllTasks(@RequestHeader("Authorization") String authHeader) {
        return validateAndGetUsername(authHeader)
                .map(username -> ResponseEntity.ok(taskRepository.findByUsername(username)));
    }

    // Get a single task by id for the authenticated user
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Task>> getTask(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        return validateAndGetUsername(authHeader)
                .map(username -> {
                    return taskRepository.findByIdAndUsername(id, username)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
                });
    }

    // Create a new task for the authenticated user
    @PostMapping
    public Mono<ResponseEntity<Task>> createTask(@Valid @RequestBody Task task, @RequestHeader("Authorization") String authHeader) {
        return validateAndGetUsername(authHeader)
                .map(username -> {
                    task.setUsername(username);
                    return ResponseEntity.ok(taskRepository.save(task));
                });
    }

    // Update a task for the authenticated user
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Task>> updateTask(@PathVariable Long id, @Valid @RequestBody Task updated, @RequestHeader("Authorization") String authHeader) {
        return validateAndGetUsername(authHeader)
                .map(username -> {
                    return taskRepository.findByIdAndUsername(id, username)
                            .map(task -> {
                                task.setTitle(updated.getTitle());
                                task.setCompleted(updated.isCompleted());
                                return ResponseEntity.ok(taskRepository.save(task));
                            })
                            .orElse(ResponseEntity.notFound().build());
                });
    }

    // Delete a task for the authenticated user
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        return validateAndGetUsername(authHeader)
                .map(username -> {
                    return taskRepository.findByIdAndUsername(id, username)
                            .map(task -> {
                                taskRepository.delete(task);
                                return ResponseEntity.noContent().<Void>build();
                            })
                            .orElse(ResponseEntity.notFound().build());
                });
    }

    /**
     * Helper method to validate JWT and extract username.
     * This demonstrates inter-service communication in microservices.
     */
    private Mono<String> validateAndGetUsername(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new RuntimeException("Invalid authorization header"));
        }
        String token = authHeader.substring(7);
        return userServiceClient.getUsernameFromToken(token);
    }
} 