# Use Eclipse Temurin JDK 21 (minimal UBI9 image)
FROM eclipse-temurin:21-jdk-ubi9-minimal

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from target folder into container
COPY target/*.jar app.jar

# Expose the Config Server port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]