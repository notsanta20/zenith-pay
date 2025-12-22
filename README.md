# 🏦 Zenith Pay

**Zenith Pay** is a **Spring Boot–based microservices banking application** designed using **real-world fintech
architecture principles** such as API Gateway security, event-driven communication with **Kafka** and centralized
authentication.

The project demonstrates **production-grade backend engineering practices** including JWT authentication with HttpOnly
cookies, service discovery, asynchronous workflows, and clean separation of concerns.

---

## 🧩 Architecture Overview

Zenith Pay follows a **microservices architecture** with:

- **API Gateway** as a single entry point
- **Service Discovery** using Eureka Server
- **Independent domain services**
- **Kafka for asynchronous side-effects**
- **Dockerized infrastructure**

All client requests pass through the **API Gateway**, which enforces authentication and forwards identity context to
downstream services.

---

## 🛠 Tech Stack

- **Language:** Java
- **Frameworks:** Spring Boot, Spring Cloud
- **Databases:**
    - PostgreSQL → Core domain services
    - MongoDB → Notifications & Logs
- **Messaging:** Apache Kafka
- **Service Discovery:** Eureka Server
- **API Gateway:** Spring Cloud Gateway
- **Authentication:** JWT (Access Token only)
- **Containerization:** Docker & Docker Compose

---

## 🔐 Authentication & Security

### Authentication Model

- **Public APIs:** Login, Register
- **Protected APIs:** All others (enforced at API Gateway)
- JWT access token stored in an **HttpOnly cookie**

### Cookie Configuration

- `HttpOnly = true`
- `SameSite = Strict`
- `Secure = true` (enabled for production profile)

### Gateway Security Flow

1. API Gateway intercepts protected requests
2. JWT is extracted from HttpOnly cookie
3. Token is validated
4. On success:
    - `userId` and `email` are extracted
    - Added to request headers
    - Forwarded to downstream services
5. On failure:
    - Request is rejected with an exception

Downstream services **only trust headers added by the gateway**, never client-provided headers.

---

## 🧠 Services Overview

| Service              | Responsibility                        | Database   |
|----------------------|---------------------------------------|------------|
| Auth Service         | User registration, login, JWT issuing | PostgreSQL |
| User Profile Service | User details & KYC status             | PostgreSQL |
| Account Service      | Account creation & management         | PostgreSQL |
| Transaction Service  | Money transfers & transactions        | PostgreSQL |
| Notification Service | User notifications                    | MongoDB    |
| Log Service          | Centralized application logs          | MongoDB    |
| API Gateway          | Security & routing                    | —          |
| Eureka Server        | Service discovery                     | —          |

All services are accessed **only via the API Gateway (port 8089)**.

---

## 🔄 Kafka Usage (Async Side-Effect Pattern)

Kafka is used as an **asynchronous side-effect mechanism**, not as a primary data source.

### Why this approach?

- Keeps APIs fast
- Decouples services
- Enables eventual consistency
- Matches real banking workflows

### Kafka Use Cases

#### 1️⃣ User Registration → Profile Creation

- Auth Service publishes `USER_REGISTERED` event
- Profile Service consumes it
- Creates a profile with:
    - `userId`
    - Other fields as `null`

This guarantees **every user has a profile** without synchronous dependencies.

---

#### 2️⃣ Account Creation → KYC Update

- Account Service publishes `ACCOUNT_CREATED` event
- Profile Service consumes it
- Updates `isKYC = true`

Account creation remains independent of profile logic.

---

#### 3️⃣ Notifications & Logs

- Domain services emit Kafka events
- Notification Service and Log Service consume events
- Data is stored asynchronously in MongoDB

---

## 📊 Global Exception Handling

Each service implements:

- A **global exception handler**
- Consistent error responses
- Proper HTTP status codes

Ensures predictable error handling across services.

---

## 🚀 Running the Application (Docker)

Zenith Pay is fully dockerized.

### Prerequisites

- Docker
- Docker Compose

---

### Environment Variables

Create a `.env` file in the project root:

```env
POSTGRES_USER="your_user"
POSTGRES_PASSWORD="your_password"
JWT_SECRET="your_hmac_sha512_key"
SPRING_PROFILES_ACTIVE=prod
```

---

### Start all services

```
docker-compose up --build
```


