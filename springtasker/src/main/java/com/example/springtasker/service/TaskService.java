package com.example.springtasker.service;

import com.example.springtasker.model.Task;
import com.example.springtasker.model.User;
import com.example.springtasker.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks for a user
    public List<Task> getAllTasks(User user) {
        return taskRepository.findByUser(user);
    }

    // Get a single task by id for a user
    public ResponseEntity<Task> getTask(Long id, User user) {
        return taskRepository.findByIdAndUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new task for a user
    public Task createTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    // Update a task for a user
    public ResponseEntity<Task> updateTask(Long id, Task updated, User user) {
        return taskRepository.findByIdAndUser(id, user)
                .map(task -> {
                    task.setTitle(updated.getTitle());
                    task.setCompleted(updated.isCompleted());
                    return ResponseEntity.ok(taskRepository.save(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a task for a user
    public ResponseEntity<Void> deleteTask(Long id, User user) {
        return taskRepository.findByIdAndUser(id, user)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
