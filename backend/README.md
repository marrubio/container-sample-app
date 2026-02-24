# ☕ Backend Module

This module implements the backend REST API for the sample application. It is built with Spring Boot and provides endpoints for managing game entities, authentication via Keycloak, and integration with PostgreSQL or H2 databases.

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (also known as Ports and Adapters) to decouple the core business logic from frameworks, UI, and external agents.

### 📂 Folder Structure

The code is organized into three main layers:

```
src/main/java/com/example/backend/
├── adapter/              # Adapters (Interaction with external world)
│   ├── in/               # Driving Adapters (Input)
│   │   └── web/          # REST Controllers
│   └── out/              # Driven Adapters (Output)
│       └── persistence/  # JPA Repositories & Implementations
├── application/          # Application Layer (Orchestration)
│   ├── service/          # Use Case Implementations
│   └── port/             # Input/Output Ports (Optional, if strict)
├── domain/               # Domain Layer (Core Business Logic)
│   ├── model/            # Entities and Value Objects
│   └── repository/       # Repository Interfaces (Ports)
└── infrastucture/        # Framework configuration & cross-cutting concerns
```

### 🧠 Key Concepts

1.  **Domain (`domain/`)**: The heart of the software. It contains enterprise business rules. It has **no dependencies** on frameworks (like Spring or JPA).
  *   **Repository Interfaces**: Defined here (e.g., `UserRepository`), but implemented in the adapter layer.

2.  **Application (`application/`)**: Orchestrates the domain logic to fulfill use cases.
  *   **Services**: Coordinators that handle transactions and direct traffic between the ports and the domain.

3.  **Adapters (`adapter/`)**: The translation layer.
  *   **In (Driving)**: Converts HTTP requests to domain calls (e.g., Rest Controllers).
  *   **Out (Driven)**: Implements domain interfaces to talk to external systems (e.g., `JpaUserRepository` implementation).

## 📦 Build and Package

Compile and package the application (without running tests):

```bash
mvn clean package -DskipTests
```

## 🚀 Running the App

```bash
mvn clean spring-boot:run
```


## ⚙️ Maven Profiles

- `default`: Uses PostgreSQL as datasource.
- `test`: Uses H2 in-memory database for testing. Activates `application-test.properties`.

To run tests with the H2 profile:

```bash
mvn test -f backend/pom.xml -Dspring.profiles.active=test
```

## 💻 Running Locally

You must provide database credentials and URL as environment variables. These are not stored in `application.properties` for security reasons.

### 🔑 Required Environment Variables

- `DB_URL`: JDBC connection URL (e.g. `jdbc:postgresql://localhost:5432/testdb?currentSchema=app`)
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `KC_BASE_URL`: Keycloak server URL

#### Example (PowerShell):
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/testdb?currentSchema=app"
$env:DB_USERNAME="user"
$env:DB_PASSWORD="password"
$env:KC_BASE_URL="https://keycloak/realms/testapp"
mvn spring-boot:run
```

#### Example (Linux/macOS):
```bash
export DB_URL="jdbc:postgresql://localhost:5432/testdb?currentSchema=app"
export DB_USERNAME="user"
export DB_PASSWORD="password"
export KC_BASE_URL="https://keycloak/realms/testapp"
mvn spring-boot:run
```

## 🐳 Docker Image

Build the Docker image using the Dockerfile in folder:

```bash
docker build -t sample-backend:latest -f Dockerfile .
```

Build image using `podman`:

```bash
podman build -t sample-backend:latest -f Dockerfile .
```

Run image locally, exposing port 3000:

```bash
podman run -p 8081:8081 sample-backend:latest
```

## 🌐 CORS Configuration for Deployment

To allow access from different origins (e.g., from the OpenShift frontend route), configure the external property `app.cors.allowed-origins`.

You can do this by adding it to your `application.properties` or as an environment variable:

**application.properties:**
```
app.cors.allowed-origins=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

**Environment Variable (OpenShift):**
```
APP_CORS_ALLOWED_ORIGINS=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

This ensures a "Build Once, Deploy Anywhere" approach without recompiling the backend for different environments.

## 🔒 Security Recommendations

- Never store database credentials in source code or properties files.
- Use environment variables, secrets, or configmaps for sensitive information.
- Limit database user privileges and restrict allowed hosts in PostgreSQL configuration.

## 🧪 Testing

- Tests use the H2 in-memory database and initialize sample data via `data.sql`.
- To verify data initialization, check that the `Game` entity contains at least one record after test startup.

### 📐 Architecture Tests (ArchUnit)

This project integrates **ArchUnit** to ensure the code adheres to the defined Hexagonal Architecture and prevents structural erosion.

**Key rules enforced:**
- **Domain Independence**: The `domain` package must not depend on `adapter`, `application`, or `infrastructure` packages.
- **Layer Direction**: Adapters can depend on Application and Domain, but not vice-versa.
- **Cycle Free**: No cyclic dependencies between packages.
- **Naming Conventions**: Controllers should end with `Controller`, Repositories with `Repository`, etc.

To run these tests specifically:

```bash
mvn test -Dtest=*ArchitectureTest
```

## 📋 Functional Overview

- REST API for game management
- Authentication via Keycloak
- Database integration (PostgreSQL/H2)

## 📝 Contribution & Documentation Guidelines

- All source code, comments, and documentation must be written in **English**.
- Any change related to configuration or startup procedures in the backend must be documented in this `README.md`.
- Follow **Clean Code** and **Clean Architecture** principles for all backend code.

## 📧 Contact

For questions or support, contact the repository maintainer.
