# container-sample-app

Sample containerized application combining a Java backend, React frontend, PostgreSQL database, and Keycloak authentication.

## Repository Structure

- `backend/` - Spring Boot REST API, database integration, Keycloak authentication
- `frontend/` - React web application
- `infra/` - Kubernetes/OpenShift deployment manifests, secrets, and configuration

## Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- Maven
- Docker
- Access to a PostgreSQL instance
- Keycloak server

### Build and Run Backend

1. Set required environment variables for database and Keycloak:
   - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `KC_BASE_URL`
2. Build and run:
   ```bash
   cd backend
   mvn clean package
   mvn spring-boot:run
   ```

### Build and Run Frontend

1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Start development server:
   ```bash
   npm run dev
   ```

### Docker Compose (optional)

You can use Docker Compose to run all services together. Example file not included, but you can create one referencing the backend, frontend, PostgreSQL, and Keycloak containers.

## Deployment

Deployment manifests for Kubernetes/OpenShift are provided in the `infra/` folder. Configure secrets and environment variables as needed for production.

## Security

- Database credentials and sensitive information must be provided via environment variables or Kubernetes secrets.
- Never store passwords in properties files or source code.

## Documentation

- [Backend module documentation](backend/README.md)
- [Frontend module documentation](frontend/README.md)

## License

This project is licensed under the MIT License.

## Contact

For questions or support, contact the repository maintainer.
