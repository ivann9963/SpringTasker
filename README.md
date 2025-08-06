🚀 SpringTasker Microservices
A modular Spring Boot ecosystem with two services, user-service and task-service, backed by PostgreSQL and orchestrated via Docker Compose.

📦 Services
Service	Port	Description
🔹 user-service	8081	REST CRUD operations for user accounts
🔹 task-service	8082	Task management with user-service integration

🗂️ Project Structure
plaintext
Copy
Edit
SpringTasker/
├── springtasker-services/     # renamed directory for clarity
│   ├── user-service/          # source code + Dockerfile
│   └── task-service/          # source code + Dockerfile
├── docker-compose.yml         # Compose orchestration
└── README.md                  # this document
⚙️ Prerequisites
Docker

Docker Compose

Java 17 (optional for local builds)

🚀 Quick Start
Clone the repo:

bash
Copy
Edit
git clone <repo-url>
cd SpringTasker
Build & launch containers:

bash
Copy
Edit
docker compose up --build -d
Verify running services:

bash
Copy
Edit
docker ps --filter "name=user-service" --filter "name=task-service"
Access APIs:

🌐 http://localhost:8081 → user-service

🌐 http://localhost:8082 → task-service

📝 TODO
Rename microservices/ directory to springtasker-services/






