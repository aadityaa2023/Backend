# 1️⃣ Build stage: Compile the Spring Boot project with Java 24
FROM eclipse-temurin:24-jdk-alpine AS build

WORKDIR /app

# Install Maven manually since alpine JDK images don't include it
RUN apk add --no-cache maven

# Copy pom.xml and download dependencies first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the Spring Boot application without running tests
RUN mvn clean package -DskipTests


# 2️⃣ Runtime stage: Run the built application in Java 24
FROM eclipse-temurin:24-jdk-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables for Render PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d2fm7cruibrs73a5ptt0-a.oregon-postgres.render.com:5432/blog_db_8gzl
ENV SPRING_DATASOURCE_USERNAME=blog_db_8gzl_user
ENV SPRING_DATASOURCE_PASSWORD=Xofj3tqTccxuMogErpupYPyZBTpFnngh
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true

# Expose backend port
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
