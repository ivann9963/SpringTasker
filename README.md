SpringTasker Microservices

A lightweight Spring Boot project providing task-service and user-service, backed by PostgreSQL.

Services

user-service: Manages user accounts (/user-service)

task-service: Manages tasks and delegates user lookups to user-service (/task-service)

Prerequisites

Docker & Docker Compose

Java 17 (for local builds without Docker)

Quick Start

# from project root
docker compose up --build -d

# services:
#  - user-service on port 8081
#  - task-service on port 8082

TODO

Rename microservices/ directory to springtasker-services/

