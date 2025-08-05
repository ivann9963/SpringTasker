# SpringTasker Microservices Architecture

This document explains how to run and test the **microservices version** of SpringTasker, which demonstrates distributed system architecture with inter-service communication.

## 🏗️ Architecture Overview

### Services
- **User Service** (Port 8081): Handles user registration, login, and JWT validation
- **Task Service** (Port 8082): Manages tasks and validates JWTs via User Service

### Key Microservices Concepts Demonstrated
- **Service Separation**: Each service has its own database and responsibility
- **Inter-Service Communication**: Task Service calls User Service to validate JWTs
- **Stateless Authentication**: JWT tokens for authentication across services
- **Independent Deployment**: Each service can be deployed separately

## 🚀 Quick Start

### 1. Start the Databases
```bash
docker-compose up -d
```
This starts:
- User Service Database (PostgreSQL on port 5432)
- Task Service Database (PostgreSQL on port 5433)

### 2. Build All Modules
```bash
mvn clean install
```

### 3. Start User Service
```bash
cd microservices/user-service
mvn spring-boot:run
```
- **Port:** 8081
- **Database:** user_service_db

### 4. Start Task Service
```bash
cd microservices/task-service
mvn spring-boot:run
```
- **Port:** 8082
- **Database:** task_service_db

## 🔧 API Testing

### Step 1: Register a User
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "testpass"}'
```

### Step 2: Login and Get JWT
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "testpass"}'
```
**Response:** `{"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."}`

### Step 3: Create a Task (using JWT)
```bash
curl -X POST http://localhost:8082/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"title": "Learn Microservices", "completed": false}'
```

### Step 4: Get User's Tasks
```bash
curl -X GET http://localhost:8082/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🔄 Inter-Service Communication Flow

### Authentication Flow
1. **Client** → **User Service**: Login with credentials
2. **User Service** → **Client**: Returns JWT token
3. **Client** → **Task Service**: Request with JWT in header
4. **Task Service** → **User Service**: Validate JWT
5. **User Service** → **Task Service**: Confirm token validity + username
6. **Task Service** → **Client**: Process request with user context

### Code Example
```java
// In TaskController.java
private Mono<String> validateAndGetUsername(String authHeader) {
    String token = authHeader.substring(7);
    return userServiceClient.getUsernameFromToken(token); // HTTP call to User Service
}
```

## 🗄️ Database Architecture

### User Service Database
- **Tables:** `users`, `roles`, `user_roles`
- **Purpose:** User management and authentication
- **Port:** 5432

### Task Service Database
- **Tables:** `tasks`
- **Purpose:** Task management only
- **Port:** 5433
- **Note:** Stores `username` (string) instead of `user_id` (foreign key)

## 🔍 Key Differences from Monolith

| Aspect | Monolith | Microservices |
|--------|----------|---------------|
| **Database** | Single PostgreSQL | Two separate databases |
| **Authentication** | Internal JWT validation | HTTP calls to User Service |
| **Deployment** | Single JAR | Two independent services |
| **Scaling** | Scale entire app | Scale services independently |
| **Fault Isolation** | Single point of failure | Isolated service failures |

## 🧪 Testing Scenarios

### 1. Service Independence
- Stop User Service → Task Service still runs (but can't validate tokens)
- Stop Task Service → User Service still runs (can still register/login)

### 2. Database Independence
- Each service has its own database
- No cross-database dependencies
- Independent data management

### 3. Inter-Service Communication
- Task Service calls User Service for JWT validation
- Demonstrates HTTP-based service communication
- Shows distributed authentication patterns

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Errors**
   ```bash
   # Check if databases are running
   docker-compose ps
   
   # Restart databases
   docker-compose restart
   ```

2. **Service Communication Errors**
   - Ensure User Service is running before Task Service
   - Check network connectivity between services
   - Verify JWT secret is shared between services

3. **Port Conflicts**
   - User Service: 8081
   - Task Service: 8082
   - User DB: 5432
   - Task DB: 5433

## 📊 Monitoring

### Health Checks
- User Service: `http://localhost:8081/actuator/health`
- Task Service: `http://localhost:8082/actuator/health`

### Logs
```bash
# User Service logs
cd microservices/user-service
mvn spring-boot:run

# Task Service logs (in new terminal)
cd microservices/task-service
mvn spring-boot:run
```

## 🎯 Learning Objectives

This microservices implementation demonstrates:

1. **Service Decomposition**: How to split a monolith into focused services
2. **Inter-Service Communication**: HTTP-based service-to-service calls
3. **Distributed Authentication**: JWT validation across services
4. **Database Per Service**: Each service owns its data
5. **Independent Deployment**: Services can be deployed separately
6. **Fault Isolation**: Services can fail independently

## 🔄 Next Steps

### Advanced Features to Add
1. **API Gateway**: Centralized routing and authentication
2. **Service Discovery**: Dynamic service registration
3. **Circuit Breakers**: Handle service failures gracefully
4. **Distributed Tracing**: Monitor request flow across services
5. **Message Queues**: Asynchronous communication
6. **Container Orchestration**: Kubernetes deployment

---

**This microservices architecture showcases modern distributed system patterns and is perfect for demonstrating your understanding of scalable, maintainable software architecture.** 