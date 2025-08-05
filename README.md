# SpringTasker - Task Management System

A comprehensive task management system demonstrating both **monolithic** and **microservices** architectures. This project showcases the evolution from a single deployable application to a distributed system with discrete services.

## 🏗️ Architecture Overview

### Monolith (`springtasker/`)
- **Single Spring Boot application** with all features
- **One database** for users and tasks
- **JWT-based authentication** with Spring Security
- **RESTful API** for user registration, login, and task management
- **Perfect for:** Small to medium applications, rapid development, simple deployment

### Microservices (`microservices/`)
- **User Service** (`user-service/`): Handles user registration, login, and JWT generation
- **Task Service** (`task-service/`): Manages tasks with user ownership validation
- **Separate databases** for each service
- **Inter-service communication** via HTTP/REST
- **Perfect for:** Scalable applications, team autonomy, independent deployment

## 📁 Project Structure

```
SpringTasker/
├── springtasker/                 # Monolith application
│   ├── src/main/java/
│   │   ├── controller/           # REST controllers
│   │   ├── model/               # JPA entities
│   │   ├── repo/                # Data repositories
│   │   ├── service/             # Business logic
│   │   └── security/            # JWT and security config
│   └── pom.xml
├── microservices/
│   ├── user-service/            # User management microservice
│   │   ├── src/main/java/
│   │   └── pom.xml
│   └── task-service/            # Task management microservice
│       ├── src/main/java/
│       └── pom.xml
├── docker-compose.yml           # Multi-service orchestration
└── pom.xml                     # Parent Maven project
```

## 🚀 Quick Start

### Prerequisites
- Java 17
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL

### Running the Monolith
```bash
cd springtasker
mvn spring-boot:run
```
- **Port:** 8080
- **Database:** PostgreSQL (configured via Docker Compose)

### Running Microservices
```bash
# Start databases
docker-compose up -d

# Build all modules
mvn clean install

# Run User Service
cd microservices/user-service
mvn spring-boot:run

# Run Task Service (in new terminal)
cd microservices/task-service
mvn spring-boot:run
```
- **User Service:** Port 8081
- **Task Service:** Port 8082

## 🔧 API Endpoints

### Monolith (Port 8080)
```
POST /auth/register     # User registration
POST /auth/login        # User login (returns JWT)
GET  /tasks            # Get user's tasks
POST /tasks            # Create new task
PUT  /tasks/{id}       # Update task
DELETE /tasks/{id}     # Delete task
```

### Microservices
**User Service (Port 8081):**
```
POST /auth/register     # User registration
POST /auth/login        # User login (returns JWT)
GET  /auth/validate     # Validate JWT (internal)
```

**Task Service (Port 8082):**
```
GET  /tasks            # Get user's tasks (requires JWT)
POST /tasks            # Create new task (requires JWT)
PUT  /tasks/{id}       # Update task (requires JWT)
DELETE /tasks/{id}     # Delete task (requires JWT)
```

## 🔐 Authentication

Both architectures use **JWT (JSON Web Tokens)** for stateless authentication:

1. **Register/Login** → Receive JWT
2. **Include JWT** in Authorization header: `Bearer <token>`
3. **Services validate** JWT before processing requests

## 🏛️ Architecture Comparison

| Aspect | Monolith | Microservices |
|--------|----------|---------------|
| **Deployment** | Single JAR | Multiple services |
| **Database** | Shared PostgreSQL | Separate databases |
| **Scaling** | Scale entire app | Scale individual services |
| **Development** | Simple setup | More complex orchestration |
| **Team Work** | Shared codebase | Independent teams |
| **Fault Isolation** | Single point of failure | Isolated failures |

## 🐳 Docker Support

```bash
# Run with Docker Compose
docker-compose up -d

# Build and run services
docker-compose -f docker-compose.yml up --build
```

## 🧪 Testing

```bash
# Test monolith
cd springtasker
mvn test

# Test microservices
mvn test -pl microservices/user-service
mvn test -pl microservices/task-service

# Test all modules
mvn test
```

## 📊 Monitoring & Observability

- **Health checks** on `/actuator/health`
- **Application metrics** via Spring Boot Actuator
- **Logging** with structured JSON format

## 🔄 CI/CD Considerations

### Monolith
- Single build pipeline
- Simple deployment strategy
- All-or-nothing deployment

### Microservices
- Independent build pipelines
- Blue-green deployments
- Service-specific rollbacks

## 🎯 Learning Objectives

This project demonstrates:

1. **Spring Boot** fundamentals
2. **JWT authentication** implementation
3. **JPA/Hibernate** data persistence
4. **RESTful API** design
5. **Microservices** architecture patterns
6. **Inter-service communication**
7. **Docker** containerization
8. **Maven** multi-module projects

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📝 License

This project is for educational purposes and demonstrates modern software architecture patterns.

---

**Built with ❤️ using Spring Boot, JWT, and Microservices Architecture** 