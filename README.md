# 💰 FinTrack — Cloud-Native Personal Finance Tracker

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-brightgreen?style=for-the-badge&logo=springboot)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.9-black?style=for-the-badge&logo=apachekafka)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20SNS%20%7C%20S3-yellow?style=for-the-badge&logo=amazonaws)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker)

> A production-grade, event-driven microservices application for tracking personal finances — built with Spring Boot, Apache Kafka, and deployed on AWS.

---

## 🏗️ Architecture

```
                        ┌─────────────────────────────────────────────┐
                        │              AWS EC2 (Mumbai)                │
                        │                                              │
   Postman / Client ───▶│  Transaction Service (8081)                  │
                        │         │                                    │
                        │         ├──▶ PostgreSQL (saves transaction)  │
                        │         │                                    │
                        │         └──▶ Apache Kafka (publishes event)  │
                        │                    │                         │
                        │         ┌──────────┴──────────┐             │
                        │         ▼                     ▼             │
                        │  Notification Service   Report Service       │
                        │      (8082)               (8083)             │
                        │         │                     │              │
                        └─────────┼─────────────────────┼─────────────┘
                                  │                     │
                                  ▼                     ▼
                             AWS SNS               AWS S3
                          (Email Alert)         (PDF Upload)
```

---

## ✨ Features

- 📊 **Transaction Management** — Create and retrieve financial transactions via REST API
- ⚡ **Event-Driven Architecture** — Apache Kafka decouples services for real-time processing
- 📧 **Instant Alerts** — AWS SNS sends email notifications for every transaction
- 📄 **PDF Reports** — Automatically generates and uploads transaction reports to AWS S3
- ☁️ **Cloud Deployed** — Fully deployed on AWS EC2 with PostgreSQL persistence
- 🔒 **Input Validation** — Request validation with meaningful error messages
- 🐳 **Dockerized** — Kafka and Zookeeper run in Docker containers

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.13 |
| Message Broker | Apache Kafka 3.9 |
| Database | PostgreSQL 17 |
| Cloud | AWS EC2, SNS, S3 |
| Containerization | Docker & Docker Compose |
| PDF Generation | iText 5.5.13 |
| Build Tool | Maven |

---

## 🚀 Microservices

### 1. Transaction Service (Port 8081)
The core service responsible for:
- Accepting transaction requests via REST API
- Persisting transactions to PostgreSQL
- Publishing transaction events to Kafka topic `transactions.events`

### 2. Notification Service (Port 8082)
Listens to Kafka events and:
- Consumes transaction events from `transactions.events`
- Sends real-time email alerts via AWS SNS

### 3. Report Service (Port 8083)
Listens to Kafka events and:
- Consumes transaction events from `transactions.events`
- Generates PDF reports using iText
- Uploads PDF reports to AWS S3

---

## 📡 API Endpoints

### Create Transaction
```http
POST /api/transactions
Content-Type: application/json

{
  "userId": "user123",
  "amount": 500.00,
  "category": "Food",
  "type": "EXPENSE"
}
```

**Response:**
```json
{
  "id": 1,
  "userId": "user123",
  "amount": 500.0,
  "category": "Food",
  "type": "EXPENSE",
  "createdAt": "2026-04-18T17:42:36"
}
```

### Get All Transactions
```http
GET /api/transactions
```

---

## 🏃 Running Locally

### Prerequisites
- Java 21+
- Maven
- Docker Desktop
- PostgreSQL
- AWS Account (for SNS & S3)

### Step 1 — Clone the repository
```bash
git clone https://github.com/Priyanshu-123/fintrack.git
cd fintrack
```

### Step 2 — Start Kafka with Docker
```bash
docker compose up -d
```

### Step 3 — Set up PostgreSQL
```sql
CREATE DATABASE fintrack;
CREATE USER fintrack_user WITH PASSWORD 'fintrack_pass';
GRANT ALL PRIVILEGES ON DATABASE fintrack TO fintrack_user;
GRANT ALL ON SCHEMA public TO fintrack_user;
```

### Step 4 — Configure AWS credentials
Set these environment variables:
```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_KEY=your_secret_key
export AWS_SNS_TOPIC_ARN=your_sns_topic_arn
```

### Step 5 — Run the services
```bash
# Terminal 1
cd transaction-service && ./mvnw spring-boot:run

# Terminal 2
cd notification-service && ./mvnw spring-boot:run

# Terminal 3
cd report-service && ./mvnw spring-boot:run
```

---

## ☁️ AWS Deployment

The application is deployed on **AWS EC2 (ap-south-1 / Mumbai)** with:

- **EC2 t3.micro** — Hosts all three Spring Boot microservices
- **PostgreSQL** — Runs on the EC2 instance
- **Kafka & Zookeeper** — Run as Docker containers on EC2
- **AWS SNS** — Managed service for email notifications
- **AWS S3** — Managed service for PDF storage (`fintrack-reports-priya-2026`)

---

## 📁 Project Structure

```
fintrack/
├── transaction-service/
│   ├── src/main/java/com/fintrack/transaction/
│   │   ├── controller/      # REST endpoints
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Data access layer
│   │   ├── model/           # JPA entities
│   │   ├── dto/             # Request/Response DTOs
│   │   └── kafka/           # Kafka producer
│   └── src/main/resources/
│       └── application.properties
├── notification-service/
│   ├── src/main/java/com/fintrack/notification/
│   │   ├── kafka/           # Kafka consumer
│   │   └── service/         # AWS SNS integration
│   └── src/main/resources/
│       └── application.properties
├── report-service/
│   ├── src/main/java/com/fintrack/report/
│   │   ├── kafka/           # Kafka consumer
│   │   └── service/         # PDF generation + S3 upload
│   └── src/main/resources/
│       └── application.properties
└── docker-compose.yml
```

---

## 🔄 Event Flow

```
1. Client sends POST /api/transactions
2. Transaction Service validates and saves to PostgreSQL
3. Transaction Service publishes JSON event to Kafka topic
4. Notification Service consumes event → sends email via SNS
5. Report Service consumes event → generates PDF → uploads to S3
```

---

## 🌱 Future Enhancements

- [ ] GitHub Actions CI/CD pipeline for auto-deployment
- [ ] AWS RDS for managed PostgreSQL
- [ ] JWT Authentication & Authorization
- [ ] Dashboard UI with React
- [ ] Spending analytics and charts
- [ ] Budget alerts when spending exceeds limits
- [ ] Multi-currency support

---

## 👨‍💻 Author

**Priyanshu Choudhary**

[![GitHub](https://img.shields.io/badge/GitHub-Priyanshu--123-black?style=flat&logo=github)](https://github.com/Priyanshu-123)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
