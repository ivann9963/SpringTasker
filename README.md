SpringTasker Microservices

A modular Spring Boot ecosystem featuring two services—user-service and task-service—each backed by PostgreSQL and easily deployable with Docker Compose.

📦 Services

Service

Port

Description

user-service

8081

CRUD operations on user accounts

task-service

8082

Task management; integrates with user-service for auth

🔧 Prerequisites

Docker & Docker Compose

Java 17 (only if you prefer local builds without Docker)

🚀 Quick Start

From the project root:

# Build and launch all services
docker compose up --build -d

# Verify running services
docker ps --filter "name=user-service" --filter "name=task-service"

👤 user-service → http://localhost:8081

✅ task-service → http://localhost:8082

📝 TODO

Rename microservices/ directory to springtasker-services/
