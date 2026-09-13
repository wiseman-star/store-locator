# ----------------------------------------------------------------------------------------------------------------------
# Build stage: compile Spring Boot application using Maven + JDK 25
# ----------------------------------------------------------------------------------------------------------------------
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy Maven wrapper and config first (layer caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Pre-download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the Spring Boot fat jar
RUN ./mvnw clean package -DskipTests


# ----------------------------------------------------------------------------------------------------------------------
# Runtime stage: lightweight JRE 25 image
# ----------------------------------------------------------------------------------------------------------------------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
