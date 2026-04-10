# TODO backend - alineación con la skill de Spring Boot

Este documento recoge los cambios recomendados para que el `backend` cumpla mejor con la skill `java-springboot`, manteniendo el enfoque actual de arquitectura hexagonal / puertos y adaptadores.

## Criterios usados

- Seguir buenas prácticas Spring Boot en DI, validación, configuración, testing y seguridad.
- No romper la separación actual entre `adapter`, `application`, `domain` e `infrastucture`.
- Priorizar primero robustez de API y mantenibilidad.

---

## P0 - Imprescindible

### 1. Inyección de dependencias por constructor

- [x] Pasar `GameController` a constructor injection.
- [x] Eliminar `@Autowired` redundante en constructores con único constructor:
  - `src/main/java/es/marugi/container/backend/application/service/GameServiceImpl.java`
  - `src/main/java/es/marugi/container/backend/adapter/out/persistence/GamePersistenceAdapter.java`
- [x] Mantener dependencias como `private final` en controllers, services y adapters.

**Objetivo:** cumplir la recomendación de constructor injection e inmutabilidad.

### 2. Validación de entrada en la capa REST

- [x] Añadir `spring-boot-starter-validation` en `pom.xml` si no está presente.
- [x] Añadir Bean Validation a:
  - `src/main/java/es/marugi/container/backend/adapter/in/rest/dto/CreateGameRequestDTO.java`
  - `src/main/java/es/marugi/container/backend/adapter/in/rest/dto/UpdateGameRequestDTO.java`
- [x] Usar `@Valid` en los endpoints que reciben `@RequestBody`:
  - `POST /games`
  - `PUT /games/{id}`
- [x] Definir reglas mínimas de validación:
  - `title`: obligatorio, no vacío
  - `description`: longitud máxima razonable
  - `developmentYear`: obligatorio o validado según contrato
  - `score`: rango válido, por ejemplo `0` a `10`

**Objetivo:** impedir payloads inválidos y cumplir la skill en `Web Layer / Validation`.

### 3. Manejo global de errores

- [x] Crear una excepción específica para recurso no encontrado:
  - por ejemplo `GameNotFoundException`
- [x] Sustituir `RuntimeException("Game not found")` en `GameServiceImpl` por una excepción explícita.
- [x] Crear un `@RestControllerAdvice` global para mapear:
  - errores de validación -> `400 Bad Request`
  - `GameNotFoundException` -> `404 Not Found`
  - errores inesperados -> `500 Internal Server Error`
- [x] Definir un formato consistente de error para la API (timestamp, status, message, path, etc.).

**Objetivo:** cumplir la skill en `Error Handling` y dejar una API consistente.

### 4. Transaccionalidad en servicios

- [x] Añadir `@Transactional(readOnly = true)` a operaciones de consulta.
- [x] Añadir `@Transactional` a operaciones de escritura:
  - `createGame(...)`
  - `updateGame(...)`
  - `deleteGame(...)`

Archivo principal:

- `src/main/java/es/marugi/container/backend/application/service/GameServiceImpl.java`

**Objetivo:** cumplir la skill en `Service Layer / Transaction Management`.

### 5. Respuestas REST consistentes

- [x] Hacer que `POST /games` devuelva `201 Created` en lugar de `200 OK`.
- [x] Evaluar devolver también cabecera `Location` con la URI del nuevo recurso.
- [x] Añadir `GET /games/{id}` para consulta individual.
- [x] Definir comportamiento esperado de `DELETE /games/{id}` cuando el recurso no exista (`404` o `204`).

Archivo principal:

- `src/main/java/es/marugi/container/backend/adapter/in/rest/controller/GameController.java`

**Objetivo:** mejorar consistencia REST según la skill.

---

## P1 - Muy recomendable

### 6. Configuración tipada con `@ConfigurationProperties`

- [x] Sustituir el uso de `@Value("${app.cors.allowed-origins}")` en `SecurityConfig`.
- [x] Crear una clase de propiedades tipadas para CORS, por ejemplo:
  - `CorsProperties`
- [x] Mapear `app.cors.allowed-origins` a una `List<String>`.

Archivos implicados:

- `src/main/java/es/marugi/container/backend/infrastucture/config/SecurityConfig.java`
- nuevo archivo de propiedades en `src/main/java/es/marugi/container/backend/infrastucture/config/`

**Objetivo:** cumplir la skill en `Configuration / Type-Safe Properties`.

### 7. Ajustes de seguridad

- [x] Mantener lectura pública sólo si es un requisito funcional real.
- [x] Revisar si `POST`, `PUT` y `DELETE` deben requerir autenticación y/o roles.
- [x] Asegurar que la API sea explícitamente stateless.
- [x] Revisar CORS para permitir sólo orígenes necesarios.

Archivo principal:

- `src/main/java/es/marugi/container/backend/infrastucture/config/SecurityConfig.java`

**Objetivo:** reforzar el cumplimiento de la skill en `Security`.

### 8. Semántica Spring en persistencia

- [x] Cambiar `@Component` por `@Repository` en `GamePersistenceAdapter`.
- [x] Mantener la separación actual:
  - `GameRepository` como puerto
  - `GameJpaRepository` como repositorio Spring Data
  - `GamePersistenceAdapter` como adaptador saliente

Archivo principal:

- `src/main/java/es/marugi/container/backend/adapter/out/persistence/GamePersistenceAdapter.java`

**Objetivo:** alinear la semántica de persistencia con la skill sin romper hexagonal.

### 9. Separar bootstrap y controller

- [x] Quitar `@RestController` de `DemoApplication` si no es necesario.
- [x] Eliminar el endpoint raíz `"/"` porque ya no se quiere mantener.

Archivo principal:

- `src/main/java/es/marugi/container/backend/DemoApplication.java`

**Objetivo:** mejorar separación de responsabilidades.

### 10. Logging útil y consistente

- [x] Añadir logging SLF4J en puntos de negocio relevantes:
  - creación
  - actualización
  - borrado
  - recursos no encontrados
- [x] Mantener logs parametrizados (`logger.info("... {}", valor)`).
- [x] Evitar concatenación de strings en logs.

Archivos candidatos:

- `src/main/java/es/marugi/container/backend/application/service/GameServiceImpl.java`
- `src/main/java/es/marugi/container/backend/adapter/in/rest/controller/GameController.java`

**Objetivo:** cumplir la skill en `Logging`.

---

## P2 - Calidad técnica / operativa

### 11. Mejorar la configuración JPA

- [ ] Añadir `spring.jpa.open-in-view=false` en propiedades si la API no necesita Open Session in View.
- [ ] Revisar `spring.jpa.hibernate.ddl-auto=update` y valorar migraciones con Flyway/Liquibase.
- [ ] Limpiar el perfil `test` para evitar warnings por mezcla de H2 con configuración PostgreSQL.
- [ ] Revisar si `spring.jpa.properties.hibernate.dialect` debe eliminarse para dejar autodetección.

Archivos implicados:

- `src/main/resources/application.properties`
- `src/main/resources/application-local.properties`
- `src/main/resources/application-test.properties`

**Objetivo:** mejorar limpieza operativa y evitar configuración innecesaria.

### 12. Reforzar tests

- [ ] Añadir unit tests de servicio con Mockito:
  - `GameServiceImplTest`
- [ ] Añadir test slice web con `@WebMvcTest`:
  - validación
  - errores HTTP
  - contrato REST
- [ ] Añadir test slice de persistencia con `@DataJpaTest`.
- [ ] Adaptar `GameControllerIntegrationTest` para usar DTOs REST / JSON en lugar de `Game` como contrato externo.
- [ ] Añadir tests para:
  - `404 Not Found`
  - `400 Bad Request`
  - seguridad de endpoints de escritura

Archivos existentes a revisar:

- `src/test/java/es/marugi/container/backend/GameControllerIntegrationTest.java`
- `src/test/java/es/marugi/container/backend/DemoApplicationTests.java`

**Objetivo:** cumplir la skill en `Testing` y validar de verdad el contrato HTTP.

### 13. Revisar mappers y limpieza técnica

- [ ] Revisar si `GameRestMapper.INSTANCE` sobra al usar `componentModel = "spring"`.
- [ ] Revisar imports no usados y pequeños desajustes de estilo.
- [ ] Corregir el nombre del paquete `infrastucture` -> `infrastructure` si se decide hacer limpieza general.

Archivos candidatos:

- `src/main/java/es/marugi/container/backend/adapter/in/rest/mapper/GameRestMapper.java`
- `src/main/java/es/marugi/container/backend/infrastucture/...`

**Objetivo:** limpieza técnica y consistencia interna.

### 14. Revisar dependencias y versionado

- [ ] Verificar si la versión usada de Spring Boot es la deseada para el proyecto.
- [ ] Comprobar compatibilidad de versiones de MapStruct, ArchUnit y plugins Maven.
- [ ] Añadir dependencias nuevas sólo si están justificadas (`validation`, Flyway, Testcontainers, etc.).

Archivo principal:

- `pom.xml`

**Objetivo:** mantener el proyecto consistente y sostenible.

---

## Orden recomendado de ejecución

1. Constructor injection y limpieza de `@Autowired`
2. Validación Bean Validation + `@Valid`
3. Excepciones + `@RestControllerAdvice`
4. `@Transactional` en servicios
5. Respuestas REST correctas (`201`, `404`, etc.)
6. `@ConfigurationProperties` para CORS
7. Ajustes de seguridad
8. Tests unitarios + `@WebMvcTest` + `@DataJpaTest`
9. Limpieza de configuración JPA
10. Limpieza técnica adicional

---

## Nota final

No todas las recomendaciones de la skill implican abandonar el enfoque hexagonal actual. La idea es:

- adoptar buenas prácticas Spring Boot,
- mantener DTOs en la capa web,
- mantener servicios de aplicación como punto de entrada del caso de uso,
- y conservar los puertos/adaptadores de persistencia ya introducidos.

