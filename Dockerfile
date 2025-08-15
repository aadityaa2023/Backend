# 1️⃣ Build Stage - use Maven to compile the Spring Boot app
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# 2️⃣ Runtime Stage - run the built app in a smaller image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy only the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables for Render PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d2fm7cruibrs73a5ptt0-a.oregon-postgres.render.com:5432/blog_db_8gzl
ENV SPRING_DATASOURCE_USERNAME=blog_db_8gzl_user
ENV SPRING_DATASOURCE_PASSWORD=Xofj3tqTccxuMogErpupYPyZBTpFnngh
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
