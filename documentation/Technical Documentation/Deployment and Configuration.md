# Deployment and Configuration

## Introduction
This document provides a high-level overview of the application, its purpose, target audience, and key features. It sets the context for the reader and explains what the document covers.

## Overview
This document describes how to deploy the application, including different environments such as staging and production. It includes information on deployment artifacts, configuration parameters, and a deployment diagram.

## Deployment Artifacts
The application is divided into two main components: frontend and backend. Both components are containerized using Docker.

### Frontend
- **Dockerfile**: The Dockerfile for the frontend is used to build the Docker image.
- **Docker Image**: The built Docker image is pushed to DockerHub.
- **Dockerfile**: The Dockerfile for the frontend is used to build the Docker image.

```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 80
CMD ["npx", "serve", "dist"]
```
### Backend
- **Dockerfile**: The Dockerfile for the backend is used to build the Docker image.
  - **Docker Image**: The built Docker image is pushed to DockerHub.
```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ../target/elections-0.0.1-SNAPSHOT.jar /app/backend-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/backend-app.jar"]
```
## Deployment Process
The deployment process involves the following steps:

1. **Build Docker Images**: Build the Docker images for both frontend and backend.
2. **Push to DockerHub**: Push the built Docker images to DockerHub.
3. **Deploy to Render**: Deploy the Docker images from DockerHub to the Render server.

### Step-by-Step Deployment

1. **Build Docker Images**:
    - Navigate to the frontend directory and build the Docker image:
      ```sh
      docker build -t your-dockerhub-username/frontend:latest .
      ```
    - Navigate to the backend directory and build the Docker image:
      ```sh
      docker build -t your-dockerhub-username/backend:latest .
      ```

2. **Push to DockerHub**:
    - Push the frontend Docker image to DockerHub:
      ```sh
      docker push your-dockerhub-username/frontend:latest
      ```
    - Push the backend Docker image to DockerHub:
      ```sh
      docker push your-dockerhub-username/backend:latest
      ```

3. **Deploy to Render**:
    - Log in to Render account.
    - Create a new web service for the frontend and configure it to pull the Docker image from DockerHub.
    - Create a new web service for the backend and configure it to pull the Docker image from DockerHub.

## Configuration Parameters
The application requires several configuration parameters, such as environment variables and database settings.

### Environment Variables
- **DATABASE_URL**: jdbc:mysql://oege.ie.hva.nl:3306/zhaverkj3
- **API_BASE_URL**: https://wiipuujaamee42-frontend.onrender.com

### Database Settings
- **DB_HOST**: The hostname of the database server.
- **DB_PORT**: 3306
- **DB_NAME**: Oege

## Deployment Diagram
Below is a deployment diagram illustrating the deployment architecture:
Mermaid-code:
```mermaid
flowchart TD
 A[DockerHub] -->|Push Frontend Image| B[Render Frontend Service]
    A -->|Push Backend Image| C[Render Backend Service]
```
![Deploy-diagram](img_6.png)