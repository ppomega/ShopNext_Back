# Use a base image with JDK 24
FROM eclipse-temurin:24-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
