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

## Running Locally

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

## Docker Image

Build the Docker image using the Dockerfile in the `docker` folder:

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

## Configuración de CORS para despliegue

Para permitir el acceso desde diferentes orígenes (por ejemplo, desde la ruta de OpenShift del frontend), configura la propiedad externa `app.cors.allowed-origins`.

Puedes hacerlo añadiendo en tu `application.properties` o como variable de entorno:

**application.properties:**
```
app.cors.allowed-origins=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

**Variable de entorno (OpenShift):**
```
APP_CORS_ALLOWED_ORIGINS=http://localhost:3000,https://frontend-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com
```

Esto permite construir una sola vez y desplegar en cualquier entorno sin recompilar el backend.

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
