FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ../target/elections-0.0.1-SNAPSHOT.jar /app/backend-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/backend-app.jar"]
