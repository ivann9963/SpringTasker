package com.example.taskservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * UserServiceClient handles communication with the User Service.
 * This is a key component in microservices architecture - inter-service communication.
 */
@Component
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(@Value("${user-service.url}") String userServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    /**
     * Validate a JWT token by calling the User Service.
     * This demonstrates inter-service communication in microservices.
     */
    public Mono<Map<String, Object>> validateToken(String token) {
        return webClient.get()
                .uri("/auth/validate")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Map.class)
                .onErrorReturn(Map.of("valid", false));
    }

    /**
     * Extract username from JWT validation response.
     */
    public Mono<String> getUsernameFromToken(String token) {
        return validateToken(token)
                .map(response -> {
                    if (Boolean.TRUE.equals(response.get("valid"))) {
                        return (String) response.get("username");
                    }
                    throw new RuntimeException("Invalid token");
                });
    }
} 