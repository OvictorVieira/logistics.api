# Logistics File Processing Service

## Overview
This project is a Kotlin-based logistics file processing service that handles file uploads, parses order data, 
and manages user, product, and order information. The service is designed to be scalable, 
with a RESTful API for file uploads and order retrieval, by processing the data with coroutines.

---

## Project Structure
```
.
├── Makefile  # Automation of common tasks
├── app       # Main application module
│   ├── build.gradle.kts  # Gradle build configuration
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   └── com
│   │   │   │       └── logistics
│   │   │   │           ├── App.kt  # Application entry point
│   │   │   │           ├── controller
│   │   │   │           │   ├── FileUploadController.kt  # Handles file uploads
│   │   │   │           │   └── OrderController.kt  # Handles order-related API endpoints
│   │   │   │           ├── domain  # Entity definitions
│   │   │   │           │   ├── Order.kt
│   │   │   │           │   ├── OrderId.kt
│   │   │   │           │   ├── ProcessingStatus.kt
│   │   │   │           │   ├── Product.kt
│   │   │   │           │   └── User.kt
│   │   │   │           ├── dto  # Data transfer objects
│   │   │   │           │   ├── mapper
│   │   │   │           │   │   ├── OrderData.kt
│   │   │   │           │   │   └── OrderMapper.kt
│   │   │   │           │   └── out
│   │   │   │           │       ├── ApiResponse.kt
│   │   │   │           │       ├── FileUploadResponse.kt
│   │   │   │           │       └── UserOrdersResponse.kt
│   │   │   │           ├── enum
│   │   │   │           │   └── ProcessingStatusEnum.kt
│   │   │   │           ├── exception  # Custom exception handling
│   │   │   │           │   ├── ExceptionHandler.kt
│   │   │   │           │   └── RecordNotFoundException.kt
│   │   │   │           ├── repository  # Data access layer (Postgres)
│   │   │   │           │   ├── OrderRepository.kt
│   │   │   │           │   ├── ProcessingStatusRepository.kt
│   │   │   │           │   ├── ProductRepository.kt
│   │   │   │           │   └── UserRepository.kt
│   │   │   │           ├── service  # Business logic (Coroutines)
│   │   │   │           │   ├── FileProcessingService.kt
│   │   │   │           │   └── OrderService.kt
│   │   │   │           └── util  # Utility classes
│   │   │   │               ├── ErrorMessageGetter.kt
│   │   │   │               └── LineParser.kt  # Parses file lines into entities
│   │   │   └── resources
│   │   │       ├── application-test.yml  # Test configurations
│   │   │       ├── application.yml  # Main configurations
│   │   │       └── db
│   │   │           └── migration  # Database schema migrations (Postgres compatible)
│   │   └── test  # Unit and integration tests
│   │       └── kotlin
│   │           └── com
│   │               └── logistics
│   │                   ├── AppTest.kt
│   │                   ├── domain
│   │                   ├── dto
│   │                   ├── enum
│   │                   ├── service
│   │                   └── util
├── development
│   └── docker
│       └── docker-compose.yml  # Docker configuration for local development (Postgres)
├── gradle  # Gradle wrapper and configurations
├── settings.gradle.kts  # Project settings
└── uploads  # Upload directory for processing files
```

---

## Features
- **File Upload API**: Accepts `.txt` files and processes them asynchronously.
- **Order Processing**: Parses order data, validates records, and stores them in Postgres.
- **User and Product Management**: Manages users and products derived from uploaded files.
- **Exception Handling**: Custom exceptions for better error responses.
- **Database Schema Migrations**: Automated schema migrations using Flyway for Postgres.

---

## Running the Project
### Prerequisites
- Docker & Docker Compose
- JDK 21
- Gradle 8.12

### Setup
1. Clone the repository.
2. Install `asdf` package manager [by clicking here](https://asdf-vm.com/guide/getting-started.html)
3. Run the commands bellow to install gradle:
   ```bash
   asdf install gradle 8.12
   ```
4. Navigate to the `development/docker` folder and run:
   ```bash
   make docker-up
   ```
   (This initializes Postgres and related services.)
5. Navigate to the project root and build the project:
   ```bash
   ./gradlew build
   ```
6. Run the application:
   ```bash
   ./gradlew bootRun
   ```

---

## API Endpoints

### Download Postman Collections

You can download the postman collection by [clicking here](https://github.com/user-attachments/files/18278719/Logistics.postman_collection.json)

### Upload File
```
POST /api/files/upload
```
Uploads a `.txt` file for processing.

Download example content file [here](https://github.com/user-attachments/files/18278722/data_1.txt)

### Get Processing Status
```
GET /api/files/processing-status/{id}
```
Retrieves the processing status of a specific file.

### Filter Orders
```
GET /api/orders/filter
```
Fetches orders based on:

- `By Order Id`
- `By Date Range`
  - `fromDate`
    - `example:` _2021-10-01_
  - `toDate`
    - `example:` _2021-12-01_

---

## API Tests

### Run Tests With Coverage
```bash
./gradlew test jacocoTestReport
```

### Coverage Report

The jacoco coverage report will be stored in `build/jacocoHtml/index.html` folder.

---
