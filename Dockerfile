FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/my-springboot-app.jar /app/my-springboot-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "my-springboot-app.jar"]
