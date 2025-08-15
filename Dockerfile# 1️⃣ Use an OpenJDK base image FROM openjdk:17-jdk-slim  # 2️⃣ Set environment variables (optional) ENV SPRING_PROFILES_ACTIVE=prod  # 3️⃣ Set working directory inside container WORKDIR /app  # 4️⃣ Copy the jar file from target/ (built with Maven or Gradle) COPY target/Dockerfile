# 1️⃣ Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# 2️⃣ Set environment variables (optional)
ENV SPRING_PROFILES_ACTIVE=prod

# 3️⃣ Set working directory inside container
WORKDIR /app

# 4️⃣ Copy the jar file from target/ (built with Maven or Gradle)
COPY target/*.jar app.jar

# 5️⃣ Expose the port your app runs on
EXPOSE 8080

# 6️⃣ Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
