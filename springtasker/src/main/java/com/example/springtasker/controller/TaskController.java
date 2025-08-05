package com.example.springtasker.controller;

import com.example.springtasker.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.springtasker.model.User;
import com.example.springtasker.repo.UserRepository;
import com.example.springtasker.service.TaskService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final UserRepository userRepository;
    private final TaskService taskService;

    public TaskController(UserRepository userRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    // Get all tasks for the authenticated user
    @GetMapping
    public List<Task> all(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return taskService.getAllTasks(user);
    }

    // Get a single task by id for the authenticated user
    @GetMapping("/{id}")
    public ResponseEntity<Task> one(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return taskService.getTask(id, user);
    }

    // Create a new task for the authenticated user
    @PostMapping
    public Task create(@Valid @RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return taskService.createTask(task, user);
    }

    // Update a task for the authenticated user
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @Valid @RequestBody Task updated, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return taskService.updateTask(id, updated, user);
    }

    // Delete a task for the authenticated user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return taskService.deleteTask(id, user);
    }
}
