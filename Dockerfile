# Multi-stage build for Spring Boot application
# Stage 1: Build the application
FROM maven:3.8-eclipse-temurin-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:11-jre

# Set the working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/spring-boot-backend-apirest-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]