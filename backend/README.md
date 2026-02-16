# Backend Module

This module implements the backend REST API for the sample application. It is built with Spring Boot and provides endpoints for managing game entities, authentication via Keycloak, and integration with PostgreSQL or H2 databases.

## Structure

- `src/main/java/es/marugi/container/backend/` - Main source code
  - `controller/` - REST controllers
  - `entity/` - JPA entities
  - `repository/` - Spring Data repositories
  - `config/` - Security and application configuration
- `src/main/resources/` - Application properties and configuration files
- `src/test/java/` - Unit and integration tests
- `docker/Dockerfile` - Docker build instructions

## Build and Package

Compile and package the application (without running tests):

```bash
mvn clean package -DskipTests
```

## Maven Profiles

- `default`: Uses PostgreSQL as datasource.
- `test`: Uses H2 in-memory database for testing. Activates `application-test.properties`.

To run tests with the H2 profile:

```bash
mvn test -f backend/pom.xml -Dspring.profiles.active=test
```

## Configuration

You must provide database credentials and URL as environment variables. These are not stored in `application.properties` for security reasons.

### Required Environment Variables

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
### Deployment Configuration

When deploying to Kubernetes/OpenShift, set these environment variables in your deployment manifests or use secrets/configmaps for sensitive data.

## Docker Image

Build the Docker image using the Dockerfile in the `docker` folder:

```bash
docker build -t sample-backend:latest -f docker/Dockerfile .
```

Build image using `podman`:

```bash
podman build -t sample-backend:latest -f docker/Dockerfile .
```

Run image locally, exposing port 3000:

```bash
podman run -p 8081:8081 sample-backend:latest
```

### Exposed Ports
- The Dockerfile exposes port `3000`. Ensure the application is configured to run on this port, or update the Dockerfile/application properties accordingly.

## Deployment in Kubernetes/OpenShift

- Define environment variables in the container spec or use secrets/configmaps for sensitive data (passwords).
- Example for setting environment variables in a deployment:

```yaml
      env:
        - name: DB_URL
          value: "jdbc:postgresql://postgresql:5432/testdb?currentSchema=app"
        - name: DB_USERNAME
          valueFrom:
            secretKeyRef:
              name: secret-postgres
              key: username
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: secret-postgres
              key: password
        - name: KC_BASE_URL
          value: "https://keycloak/realms/testapp"
```

## CORS configuration for deployment

To allow access from different origins (for example, from the OpenShift frontend route), configure the external property `app.cors.allowed-origins`.

You can do this by adding it to your `application.properties` or as an environment variable:

**application.properties:**
```
app.cors.allowed-origins=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

**Environment variable (OpenShift):**
```
APP_CORS_ALLOWED_ORIGINS=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

This allows you to build once and deploy to any environment without recompiling the backend.

## Keycloak configuration for deployment

To ensure authentication works correctly, you must configure the `KC_BASE_URL` environment variable in the backend deployment manifest (`deploy.yaml`). This variable must point to the base URL of your Keycloak instance and the corresponding realm. If it is not set correctly, the backend will not be able to validate JWT tokens and all protected endpoints will return `403 Forbidden`.

### Example configuration in `deploy.yaml`:

```yaml
      env:
        - name: KC_BASE_URL
          value: "http://keycloak/realms/testapp"
```

Make sure the URL matches exactly your Keycloak instance and the realm you are using. Adjust this value according to the environment (development, testing, production, etc.).

> **Important:** If you change the environment or Keycloak instance, always review and update this variable to avoid authentication issues.

## Security Recommendations

- Never store database credentials in source code or properties files.
- Use environment variables, secrets, or configmaps for sensitive information.
- Limit database user privileges and restrict allowed hosts in PostgreSQL configuration.

## Testing

- Tests use the H2 in-memory database and initialize sample data via `data.sql`.
- To verify data initialization, check that the `Game` entity contains at least one record after test startup.

## Functional Overview

- REST API for game management
- Authentication via Keycloak
- Database integration (PostgreSQL/H2)

## Contact

For questions or support, contact the repository maintainer.

