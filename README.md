SpringTasker Microservices

A modular Spring Boot application with user-service and task-service, backed by PostgreSQL and Docker Compose.

📦 Services

Service

Port

Description

user-service

8081

CRUD operations for user accounts

task-service

8082

Task management and user integration

🗂️ Repository Structure

SpringTasker/
├── springtasker-services/
│   ├── user-service/
│   │   └── Dockerfile
│   └── task-service/
│       └── Dockerfile
├── docker-compose.yml
└── README.md

⚙️ Prerequisites

Docker & Docker Compose

Java 17 (optional for local development)

🚀 Getting Started

Clone the repository

git clone <repo-url>
cd SpringTasker

Build & run services

docker compose up --build -d

Verify containers

docker ps --filter "name=user-service" --filter "name=task-service"

Access the APIs

http://localhost:8081

http://localhost:8082

📝 TODO

Rename the microservices/ directory to springtasker-services/
