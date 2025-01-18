# Use official OpenJDK image from DockerHub
FROM openjdk:23-jdk-slim as builder

# Set working directory in container
WORKDIR /app

# Copy the built jar file to the container
COPY build/libs/docgen-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the DocGen Service runs on
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
