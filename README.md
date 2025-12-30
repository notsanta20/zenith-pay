# 🏦 Zenith Pay

![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Dockerized-blue?logo=postgresql)
![MongoDB](https://img.shields.io/badge/MongoDB-Notifications%20%7C%20Logs-green?logo=mongodb)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event--Driven-black?logo=apachekafka)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)

---

## 🏗️ Overview

**Zenith Pay** is a **banking backend platform** built using a **Spring Boot microservices architecture**, following real-world fintech design patterns.

The system provides services for authentication, user profiles, accounts, transactions, notifications, and logs.  
All services are secured via an **API Gateway**, communicate asynchronously using **Apache Kafka**, and are fully containerized using **Docker Compose**.

---

## 🔗 Frontend Repository

The backend is consumed by the **Zenith Pay Frontend**, a secure and responsive React single-page application with backend-driven onboarding and protected routing.

👉 **Frontend Repository:** https://github.com/your-username/zenith-pay-frontend

---

## 🔹 Microservices

| Service Name             | Description |
|--------------------------|-------------|
| **auth-service**         | Handles user registration, login, and JWT-based authentication. |
| **user-service**         | Manages user profile details and KYC status. |
| **account-service**      | Handles bank account creation and management. |
| **transaction-service**  | Processes transactions and maintains transaction records. |
| **notification-service** | Consumes Kafka events to create and manage user notifications, persisted in MongoDB. |
| **log-service**          | Consumes Kafka events for centralized application and error logging, stored in MongoDB. |
| **api-gateway**          | Secures and routes requests to backend services. |
| **service-registry**     | Service discovery using Eureka Server. |

---

## ⚙️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Cloud
- **Databases:**
    - PostgreSQL (core banking data)
    - MongoDB (notifications & logs)
- **Messaging:** Apache Kafka
- **Authentication:** JWT (HttpOnly cookies)
- **Service Discovery:** Spring Cloud Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Containerization:** Docker, Docker Compose
- **Testing:** JUnit

---

## 🔐 Security & Authentication

- JWT-based authentication
- Tokens stored in **HttpOnly cookies**
- Login and Register APIs are public
- All other APIs are protected at the **API Gateway**
- Gateway validates JWT and injects `userId` and `email` into request headers
- Centralized exception handling across all services

---

## 🔄 Kafka Usage (Async Side-Effects)

Kafka is used for **asynchronous inter-service communication**:

- User registration → profile initialization
- Account creation → KYC status update
- Event-driven notifications
- Centralized logging

Kafka is not used as a primary data store; core APIs remain synchronous.

### 📥 Kafka Consumers

- **notification-service** subscribes to domain events (transactions, account updates, security events) and persists user-facing notifications in MongoDB.
- **log-service** subscribes to application and error events from all services and stores structured logs in MongoDB for observability and auditing.


---

## 🚀 Getting Started

### 1️⃣ Prerequisites
- Java 17+
- Docker & Docker Compose

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/notsanta20/zenith-pay
cd zenith-pay
````

### 3️⃣ Environment Variables
Create a `.env` file in the project root:
```bash
POSTGRES_USER="your_user"
POSTGRES_PASSWORD="your_password"
JWT_SECRET="your_hmac_sha512_key"
SPRING_PROFILES_ACTIVE=prod
````

### 4️⃣ Start all services
```
docker-compose up --build
```


