# 🚀 SpringTasker Microservices

> A modular Spring Boot application composed of two microservices—**user-service** and **task-service**—backed by PostgreSQL and managed with Docker Compose.

---

## 📦 Services

| Service           | Port | Description                                |
|-------------------|------|--------------------------------------------|
| 🔹 **user-service**  | 8081 | REST CRUD operations for user accounts     |
| 🔹 **task-service**  | 8082 | Task management + user-service integration |

---

## 🗂️ Repository Structure

```text
SpringTasker/
├── springtasker-services/     # renamed directory for clarity
│   ├── user-service/          # source code + Dockerfile
│   │   └── Dockerfile
│   └── task-service/          # source code + Dockerfile
│       └── Dockerfile
├── docker-compose.yml         # Compose orchestration
└── README.md                  # this document


⚙️ Prerequisites
Docker

Docker Compose

Java 17 (optional for local builds)

🚀 Quick Start
Clone the repo

bash
Copy
Edit
git clone <repo-url>
cd SpringTasker
Build & launch containers

bash
Copy
Edit
docker compose up --build -d
Verify services

bash
Copy
Edit
docker ps --filter "name=user-service" --filter "name=task-service"
Access the APIs

🌐 http://localhost:8081 — user-service

🌐 http://localhost:8082 — task-service

📝 TODO
Rename the microservices/ directory to springtasker-services/

