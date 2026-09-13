# Store Locator Service

Spring Boot service for finding the closest Jumbo stores to a given latitude and longitude.

The application starts with an in-memory H2 database and seeds store data from `src/main/resources/stores.json` through a Flyway Java migration.

## Tech Stack

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Flyway
- H2 in-memory database
- Springdoc OpenAPI
- JUnit 5 and Mockito
- Docker multi-stage build with Eclipse Temurin 25

## Project Structure

```text
src/main/java/com/jumbo/store/locator
  core/geo      Distance calculation domain service
  core/store    Store API, controller, service, repository, DTOs and mapper
  db            Flyway Java migration and seed helpers
  exception     Global API exception handling

src/main/resources
  application.yml
  stores.json

src/test/java
  Unit tests for store lookup and distance calculation
```

## Requirements

- JDK 25
- Maven, or the included Maven wrapper
- Docker, optional

Make sure `JAVA_HOME` points to a JDK 25 installation and that `java` is available on your `PATH`.

## Run Locally

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

The service starts on:

```text
http://localhost:8080
```

## API

### Find Closest Stores

```http
POST /api/v1/stores/closest
Content-Type: application/json
```

Request body:

```json
{
  "latitude": 52.3676,
  "longitude": 4.9041,
  "numberOfStores": 5
}
```

Validation rules:

- `latitude`: required, between `-90` and `90`
- `longitude`: required, between `-180` and `180`
- `numberOfStores`: required, between `1` and `100`

Example curl:

```bash
curl -X POST http://localhost:8080/api/v1/stores/closest \
  -H "Content-Type: application/json" \
  -d '{"latitude":52.3676,"longitude":4.9041,"numberOfStores":5}'
```

Example success response shape:

```json
{
  "success": true,
  "message": "Stores found successfully",
  "data": {
    "stores": [
      {
        "city": "Amsterdam",
        "postalCode": "1000 AA",
        "street": "Example Street",
        "distanceInKm": 1.25,
        "longitude": 4.9041,
        "latitude": 52.3676
      }
    ]
  },
  "timestamp": "2026-09-13T12:00:00"
}
```

## Error Responses

Errors use a common response body:

```json
{
  "message": "latitude: must be greater than or equal to -90",
  "errorCode": "VALIDATION_ERROR",
  "timestamp": "2026-09-13T12:00:00"
}
```

Common status codes:

- `400`: invalid request payload
- `404`: no stores found
- `500`: unexpected server error

## OpenAPI

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## H2 Database

H2 console:

```text
http://localhost:8080/h2-console
```

Connection settings:

```text
JDBC URL: jdbc:h2:mem:jumbo-db
Username: sa
Password: sa
```

The database is in-memory, so data is recreated from `stores.json` whenever the application starts.

## Tests

Run the unit test suite:

On Windows:

```powershell
.\mvnw.cmd test
```

On macOS/Linux:

```bash
./mvnw test
```

Current unit coverage includes:

- `GeoGeoDistanceServiceImpl`: Haversine distance calculation
- `StoreServiceImpl`: closest-store sorting, limiting, and empty-store handling

## Build

Create the Spring Boot jar:

```bash
./mvnw clean package
```

On Windows:

```powershell
.\mvnw.cmd clean package
```

The jar is written to `target/`.

## Docker

Build the image:

```bash
docker build -t store-locator-service .
```

Run the container:

```bash
docker run --rm -p 8080:8080 store-locator-service
```

## Implementation Notes

- Store data is loaded by `V1__SeedStores`, a Flyway Java migration.
- Distance is calculated with the Haversine formula using an earth radius of `6371` km.
- `StoreServiceImpl` retrieves all stores, calculates `distanceInKm`, sorts by distance from the requested point, limits the result count, and maps entities to DTOs.
- There is no active authentication filter in the current codebase.
