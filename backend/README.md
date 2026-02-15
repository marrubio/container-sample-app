# Backend

## Compile and package the application

```bash
mvn clean package -DskipTests
```

## Run tests

```bash
mvn test -f backend/pom.xml -D'spring.profiles.active=test'
```

## Create docker image using Dockerfile in /docker folder

```bash
docker build -t sample-backend:latest -f docker/Dockerfile .
```

# Backend - Database credentials and connection configuration

For security reasons, database credentials and URL are **not stored in the `application.properties` file**. Instead, they must be defined as environment variables before starting the application:

- `DB_URL`: JDBC connection URL, e.g. `jdbc:postgresql://localhost:5432/testdb?currentSchema=app`
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `KC_BASE_URL`: Keycloak server URL

Spring Boot will automatically pick them up at startup.

**Example in PowerShell:**
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/testdb?currentSchema=app"
$env:DB_USERNAME="user"
$env:DB_PASSWORD="password"
$env:KC_BASE_URL="https://keycloak/realms/testapp"
mvn spring-boot:run
```

**Example in Linux/macOS:**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/testdb?currentSchema=app"
export DB_USERNAME="user"
export DB_PASSWORD="password"
export KC_BASE_URL="https://keycloak/realms/testapp"
mvn spring-boot:run
```

> **Note:** If you use Docker, Kubernetes or OpenShift, define these variables in the container environment or as secrets/configmaps.
