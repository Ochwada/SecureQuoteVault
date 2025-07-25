#=================================================================================
#  Dockerfile: Multi-Stage Build for Spring Boot + Docker
#
#  Author: Ochwada
#  Date: 2025-07-24
#=================================================================================

# ========================
# 1. BUILD STAGE
# Heavy wait because it has maven.
# ========================
# Use Maven with Java 17 (Eclipse Temurin) on Alpine Linux for a lightweight build image
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Set the working directory inside the container to /app
# All subsequent commands (like COPY or RUN) will be relative to this directory
WORKDIR /app

# Copy all files and folders from the current host directory into the container's /app directory.
COPY . .

# Build the application using Maven, skipping tests to speed up the build
# The output will be a .jar file inside /app/target/
RUN mvn clean package -DskipTests

# ========================
# 2. RUN STAGE
# ========================

# Use a minimal Java 17 JDK Alpine image for running the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the runtime container
WORKDIR /app

# Copy the generated JAR file from the build stage into the runtime container
COPY --from=build /app/target/*.jar app.jar

#Expose the default Spring Boot port
EXPOSE 9090

# Define the entrypoint command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]