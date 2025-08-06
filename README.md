SpringTasker Microservices

SpringTasker is a modular Spring Boot application featuring two microservices—user-service and task-service—backed by PostgreSQL and orchestrated with Docker Compose.

🔍 Overview

user-service (port 8081):

CRUD operations for user accounts via REST endpoints

task-service (port 8082):

Task creation, assignment, and retrieval

Integrates with user-service to fetch user details

📁 Repository Structure

SpringTasker/
├── springtasker-services/
│   ├── user-service/      # source and Dockerfile
│   └── task-service/      # source and Dockerfile
├── docker-compose.yml     # orchestrates all services
└── README.md              # this file

⚙️ Prerequisites

Docker & Docker Compose

Java 17 (optional for local builds)

🚀 Getting Started

Clone the repository

git clone <repo-url>
cd SpringTasker

Build & launch services

docker compose up --build -d

Verify running containers

docker ps --filter "name=user-service" --filter "name=task-service"

Access the APIs

http://localhost:8081 → user-service

http://localhost:8082 → task-service

📝 TODO

Rename microservices/ directory to springtasker-services/
