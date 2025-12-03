# 👥 Users API - Complete REST API with Spring Boot

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen) ![Maven](https://img.shields.io/badge/Maven-3.6+-blue) ![H2 Database](https://img.shields.io/badge/H2-In--Memory-lightblue) ![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-success) ![WireMock](https://img.shields.io/badge/WireMock-Testing-red)

## 📋 Description

Users API is a complete REST API for user management, built with **Spring Boot 3** and **Java 17**. The project features a comprehensive user CRUD system with data validation, automatic OpenAPI documentation, H2 in-memory database, and complete WireMock testing suite.

The application was developed as a learning project to demonstrate competencies in modern Java backend development, following industry best practices for API design, documentation, and testing.

## 🎯 Project Purpose

This Users API was developed to demonstrate competencies in:

• **Modern REST API development** with Spring Boot 3
• **Complete CRUD operations** (Create, Read, Update, Delete) 
• **Data validation** with Bean Validation (JSR-303)
• **Automatic API documentation** with OpenAPI/Swagger
• **In-memory database management** with H2
• **Comprehensive testing** with WireMock mocking
• **Clean architecture** following MVC pattern
• **Error handling** and exception management
• **Professional code documentation** and comments

## ✨ Key Features

• **🔧 Complete CRUD** - Create, list, search, update, and delete users
• **✅ Data Validation** - Bean Validation on all fields with custom messages
• **📚 Interactive Documentation** - Swagger UI with live API testing
• **🗄️ H2 Database** - In-memory database with web console
• **🎭 WireMock Testing** - Complete API simulation for testing
• **⚠️ Error Handling** - Standardized responses for all scenarios  
• **📖 Detailed Logging** - Comprehensive operation tracking
• **🏗️ Clean Architecture** - Well-structured layers (Controller → Service → Repository)
• **🔒 Security Validations** - Email uniqueness, data integrity
• **📄 JSON Responses** - RESTful JSON API with proper HTTP status codes

## 🏗️ Architecture & Specifications

### Technology Stack

**Backend & Core:**
• **Java 17** - Programming language
• **Spring Boot 3.2.0** - Main framework  
• **Spring Web** - REST API framework
• **Spring Data JPA** - Database persistence
• **Spring Validation** - Data validation
• **Lombok** - Boilerplate reduction

**Database:**
• **H2 Database** - In-memory database for development
• **JPA/Hibernate** - Object-relational mapping

**Documentation & Testing:**
• **SpringDoc OpenAPI 2.2.0** - API documentation
• **Swagger UI** - Interactive documentation
• **WireMock 3.0.1** - API testing and mocking
• **JUnit 5** - Unit testing framework

**Build & DevTools:**
• **Maven 3.6+** - Dependency management and build
• **SLF4J + Logback** - Logging framework

### Folder Structure

```
users-openapi-wiremock-demo/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/usersapi/
│  │  │  ├─ UsersApiApplication.java        # 🚀 Main Spring Boot application
│  │  │  ├─ config/
│  │  │  │  └─ OpenApiConfig.java          # ⚙️ OpenAPI/Swagger configuration
│  │  │  ├─ controller/
│  │  │  │  ├─ UserController.java         # 🎮 REST endpoints
│  │  │  │  └─ GlobalExceptionHandler.java # ⚠️ Error handling
│  │  │  ├─ dto/
│  │  │  │  ├─ CreateUserRequest.java      # 📝 User creation DTO
│  │  │  │  ├─ UpdateUserRequest.java      # ✏️ User update DTO
│  │  │  │  └─ UserResponse.java           # 📤 User response DTO
│  │  │  ├─ model/
│  │  │  │  └─ User.java                   # 🏗️ JPA entity
│  │  │  ├─ repository/
│  │  │  │  └─ UserRepository.java         # 🗄️ Data access layer
│  │  │  └─ service/
│  │  │     └─ UserService.java            # 🔧 Business logic
│  │  └─ resources/
│  │     ├─ application.yml                # ⚙️ Application configuration
│  │     └─ data.sql                       # 📊 Initial data
│  └─ test/
│     └─ java/com/example/usersapi/
│        └─ WireMockUsersApiTest.java       # 🧪 WireMock integration tests
├─ docs/
│  ├─ openapi.yaml                         # 📄 OpenAPI specification
│  └─ WIREMOCK-SCREENSHOTS.md             # 📸 Testing documentation
├─ pom.xml                                 # 📦 Maven configuration
└─ README.md                               # 📖 This documentation
```

### Data Flow

```
┌──────────────┐
│   Client     │ (Postman, Swagger UI, Browser)
└──────┬───────┘
       │ (HTTP Requests)
       ▼
┌──────────────────────┐
│ UserController       │ (REST Layer)
│ @RestController      │
└──────┬───────────────┘
       │ (Method Calls)
       ▼
┌──────────────────────┐
│ UserService          │ (Business Layer)
│ @Service             │
└──────┬───────────────┘
       │ (JPA Operations)
       ▼
┌──────────────────────┐
│ UserRepository       │ (Data Access Layer)
│ @Repository          │
└──────┬───────────────┘
       │ (SQL Queries)
       ▼
┌─────────────────┐
│ H2 Database     │ (In-Memory Storage)
│ (data.sql)      │
└─────────────────┘
```

## 📦 API Endpoints in Detail

### 1️⃣ User Management Operations

**Base URL:** `http://localhost:8080`

| Method | Endpoint | Description | Request Body | Response Code |
|--------|----------|-------------|--------------|---------------|
| `GET` | `/users` | List all users | None | `200 OK` |
| `GET` | `/users/{id}` | Get user by ID | None | `200 OK` / `404 Not Found` |
| `POST` | `/users` | Create new user | JSON user data | `201 Created` / `400 Bad Request` |
| `PUT` | `/users/{id}` | Update user | JSON user data | `200 OK` / `400 Bad Request` / `404 Not Found` |
| `DELETE` | `/users/{id}` | Delete user | None | `204 No Content` / `404 Not Found` |

### 2️⃣ Request/Response Examples

**Create User (POST /users):**
```json
{
  "name": "Alice Johnson", 
  "email": "alice@example.com",
  "age": 28,
  "phone": "+1234567890"
}
```

**Response (201 Created):**
```json
{
  "id": 6,
  "name": "Alice Johnson",
  "email": "alice@example.com", 
  "age": 28,
  "phone": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 3️⃣ Validation Rules

**Required Fields:**
• **Name**: 2-100 characters, not blank
• **Email**: Valid email format, unique in system, max 150 characters

**Optional Fields:**
• **Age**: Positive integer
• **Phone**: Max 20 characters

### 4️⃣ Error Responses

**Validation Error (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid data provided",
  "errors": {
    "name": "Name is required",
    "email": "Email must be valid"
  }
}
```

**User Not Found (404 Not Found):**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with ID: 999"
}
```

## 🚀 Getting Started

### Prerequisites

• **Java 17** - Download from [Adoptium](https://adoptium.net/)
• **Maven 3.6+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)  
• **Git** - Download from [Git SCM](https://git-scm.com/)
• **IDE** - IntelliJ IDEA, Eclipse, or VS Code (optional but recommended)

### Local Installation

```bash
# 1. Clone the repository
git clone https://github.com/jguerradasilva/users-openapi-wiremock-demo.git
cd users-openapi-wiremock-demo

# 2. Install dependencies
mvn clean install

# 3. Configure environment variables (already configured)
# Check application.yml for H2 settings

# 4. Run the application
mvn spring-boot:run
```

**Application URLs:**
• Application: [http://localhost:8080](http://localhost:8080)
• Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
• H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Development

#### Option 1: Local Development (Recommended)

```bash
# Terminal 1: Run the application
mvn spring-boot:run

# Application will start on http://localhost:8080
# Pre-loaded with sample data from data.sql
```

#### Option 2: IDE Development

1. **Import project** into IntelliJ IDEA or Eclipse
2. **Run** `UsersApiApplication.java` main method
3. **Access** Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testing

```bash
# Run all tests
mvn test

# Run WireMock tests only
mvn test -Dtest=WireMockUsersApiTest

# Run specific test method
mvn test -Dtest=WireMockUsersApiTest#testGetAllUsers
```

## 📚 API Documentation

### Swagger UI

Access the interactive API documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**Features:**
• Visual interface for all endpoints
• Try-it-out functionality for testing
• Request/response examples
• Schema definitions
• Error response examples

### OpenAPI Specification

• **YAML File**: `/docs/openapi.yaml`
• **JSON Endpoint**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
• **Download**: Available through Swagger UI interface

### H2 Database Console

Access the database console at:
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Login Credentials:**
• **JDBC URL**: `jdbc:h2:mem:testdb`
• **Username**: `sa`
• **Password**: (leave empty)

**Available Tables:**
• `users` - Main user table with sample data

## 🧪 WireMock Testing

### Running Tests

```bash
# Execute all WireMock tests
mvn test -Dtest=WireMockUsersApiTest

# Execute specific test
mvn test -Dtest=WireMockUsersApiTest#testCreateUser

# View test output with details
mvn test -Dtest=WireMockUsersApiTest -X
```

### Test Scenarios

**Covered Test Cases:**
1. **GET /users** - List all users
2. **GET /users/{id}** - Get user by ID
3. **POST /users** - Create new user
4. **PUT /users/{id}** - Update existing user  
5. **DELETE /users/{id}** - Delete user
6. **Error scenarios** - 404 Not Found handling

### Test Output Examples

```
🚀 WireMock Server started at: http://localhost:9999

📋 Testing GET /users - List users
✅ Status: 200
📄 Response: [{"id":1,"name":"João Silva","email":"joao@email.com"...}]

➕ Testing POST /users - Create user
✅ Status: 201
📤 Request: {"name":"Pedro Oliveira","email":"pedro@email.com"...}
📥 Response: {"id":3,"name":"Pedro Oliveira","email":"pedro@email.com"...}
```

### Screenshots

For detailed testing documentation and screenshots, see:
[/docs/WIREMOCK-SCREENSHOTS.md](/docs/WIREMOCK-SCREENSHOTS.md)

## 📋 Available Scripts

```bash
# Development
mvn spring-boot:run          # Start application
mvn clean compile           # Compile source code
mvn clean install          # Install dependencies

# Testing
mvn test                   # Run all tests
mvn test -Dtest=WireMockUsersApiTest  # Run WireMock tests only

# Build & Package
mvn clean package          # Create JAR file
mvn spring-boot:build-image # Create Docker image (if Docker available)

# Code Quality
mvn clean verify          # Run all validations
```

## 🛠️ Troubleshooting

### Port 8080 Already in Use

```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9  # macOS/Linux
netstat -ano | findstr :8080   # Windows (PowerShell)
```

### Maven Issues

```bash
# Clear Maven cache
mvn clean
rm -rf ~/.m2/repository  # Remove local repository

# Reinstall dependencies
mvn clean install
```

### Java Version Issues

```bash
# Check Java version
java -version

# Should show "openjdk version 17.x.x"
# If not, install Java 17 from https://adoptium.net/
```

### H2 Console Access Issues

**Common Solutions:**
1. Ensure application is running (`mvn spring-boot:run`)
2. Use exact JDBC URL: `jdbc:h2:mem:testdb`
3. Leave password field empty
4. Try alternative URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Tests Failing

**Debugging Steps:**
1. **Check port availability**: Ensure port 9999 is free for WireMock
2. **Run individual tests**: `mvn test -Dtest=WireMockUsersApiTest#testGetAllUsers`
3. **Check logs**: Look for detailed error messages in test output
4. **Firewall issues**: Ensure firewall allows localhost connections

## 📊 Technologies in Detail

### Core Dependencies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming Language |
| Spring Boot | 3.2.0 | Application Framework |
| Spring Web | 3.2.0 | REST API Support |
| Spring Data JPA | 3.2.0 | Database Abstraction |
| Spring Validation | 3.2.0 | Data Validation |
| H2 Database | 2.2.224 | In-Memory Database |
| Lombok | 1.18.30 | Code Generation |
| SpringDoc OpenAPI | 2.2.0 | API Documentation |
| WireMock | 3.0.1 | API Testing |

### DevTools & Build

| Tool | Version | Purpose |
|------|---------|---------|
| Maven | 3.6+ | Build & Dependency Management |
| JUnit 5 | 5.10.1 | Unit Testing Framework |
| SLF4J | 2.0.9 | Logging Facade |
| Logback | 1.4.14 | Logging Implementation |

## 🎓 Learning Path & Best Practices

### Implemented Patterns

1. **MVC Architecture** - Separation of concerns with clear layers
2. **DTO Pattern** - Data transfer objects for API contracts
3. **Repository Pattern** - Data access abstraction
4. **Service Layer** - Business logic encapsulation
5. **Exception Handling** - Centralized error management
6. **Validation** - Bean Validation with custom messages
7. **Documentation** - Self-documenting API with OpenAPI
8. **Testing** - Comprehensive testing with WireMock

### Architectural Decisions

• **Spring Boot chosen for**: Rapid development, auto-configuration, extensive ecosystem
• **H2 chosen for**: Zero-configuration database, perfect for learning and testing
• **OpenAPI chosen for**: Industry-standard documentation, interactive testing interface
• **WireMock chosen for**: Reliable testing without external dependencies
• **Lombok chosen for**: Reduced boilerplate code, cleaner classes
• **Maven chosen for**: Industry-standard build tool, excellent Spring Boot integration

### Code Quality Features

• **Clean Code** - Readable, well-commented code with meaningful names
• **SOLID Principles** - Single responsibility, dependency inversion
• **Fail-Fast** - Early validation and clear error messages
• **Defensive Programming** - Input validation and error handling
• **Logging** - Comprehensive logging for debugging and monitoring

## 🔗 Useful Links

• **📱 Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
• **🗄️ H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
• **📄 OpenAPI Spec**: [/docs/openapi.yaml](/docs/openapi.yaml)
• **🧪 WireMock Guide**: [/docs/WIREMOCK-SCREENSHOTS.md](/docs/WIREMOCK-SCREENSHOTS.md)
• **📚 Spring Boot Docs**: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
• **🎯 OpenAPI Docs**: [https://swagger.io/specification/](https://swagger.io/specification/)
• **☕ Java 17 Docs**: [https://docs.oracle.com/en/java/javase/17/](https://docs.oracle.com/en/java/javase/17/)

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 💬 Support

For questions or issues:

1. Check the **Troubleshooting** section above
2. Review the **WireMock Screenshots** documentation
3. Open an [Issue](https://github.com/jguerradasilva/users-openapi-wiremock-demo/issues) on GitHub
4. Contact the development team

## ✅ Development Checklist

- [x] **Spring Boot 3** setup with Java 17
- [x] **Complete CRUD** operations for users
- [x] **Data validation** with Bean Validation
- [x] **H2 database** configuration and sample data
- [x] **OpenAPI documentation** with Swagger UI
- [x] **WireMock testing** suite with all endpoints
- [x] **Error handling** with global exception handler
- [x] **Logging** configuration for debugging
- [x] **Clean architecture** following MVC pattern
- [x] **Professional documentation** with examples
- [x] **Repository setup** with Git integration
- [x] **Maven configuration** with all dependencies

---

**Last updated**: December 2025