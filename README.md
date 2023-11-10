# DevOps Project README

## Project Overview
This DevOps project aims to streamline the development, testing, and deployment processes for a web application using a variety of technologies. The project stack includes Spring Boot for the backend, Angular for the frontend, and a set of DevOps tools for automation, testing, and monitoring.

## Technologies Used
- **Backend Framework:**
  - Spring Boot: A powerful and convention-over-configuration-based framework for building Java-based, production-grade applications.

- **Frontend Framework:**
  - Angular: A TypeScript-based open-source framework for building dynamic, single-page web applications.

- **Testing Frameworks:**
  - JUnit: A widely used testing framework for Java applications.
  - Mockito: A mocking framework for Java, useful for unit testing.

- **Continuous Integration/Continuous Deployment (CI/CD):**
  - Jenkins: An open-source automation server to support building, testing, and deploying projects.
  - SonarQube: A platform for continuous inspection of code quality.
  - Nexus: A repository manager for storing and managing artifacts.

- **Containerization:**
  - Docker: A platform for automating the deployment of applications inside lightweight, portable containers.
  - Docker Compose: A tool for defining and running multi-container Docker applications.

- **Orchestration:**
  - Kubernetes: An open-source container orchestration platform for automating the deployment, scaling, and management of containerized applications.

- **Configuration Management:**
  - Ansible: An open-source automation tool for configuration management, application deployment, and task automation.

- **Monitoring and Logging:**
  - Prometheus: An open-source systems monitoring and alerting toolkit.
  - Grafana: A multi-platform open-source analytics and interactive visualization web application.

- **Container Registry:**
  - Docker Hub: A cloud-based registry service for building and sharing containerized applications.

## Setup and Installation

### Prerequisites
- Ensure you have Java and Maven installed for Spring Boot.
- Node.js and npm are required for Angular.
- Docker and Docker Compose for containerization.
- Jenkins, SonarQube, and Nexus instances set up and configured.
- Kubernetes cluster up and running.
- Ansible installed for configuration management.

### Project Setup
1. Clone the repository:
    ```bash
    git clone <repository_url>
    ```

2. Build and run the Spring Boot backend:
    ```bash
    cd backend
    ./mvnw clean install
    java -jar target/<your_project_name>.jar
    ```

3. Build and run the Angular frontend:
    ```bash
    cd frontend
    npm install
    ng serve
    ```

4. Set up Jenkins pipeline:
    - Create a new Jenkins job with the appropriate configuration for building and deploying the application.

5. Configure SonarQube and Nexus:
    - Ensure the SonarQube and Nexus servers are running and configure the project settings accordingly.

6. Containerize the application:
    ```bash
    docker build -t <your_docker_image_name> .
    ```

7. Use Docker Compose to run the containers:
    ```bash
    docker-compose up -d
    ```

8. Deploy to Kubernetes using kubectl (or with ansible playbook in jenkins).

9. Configure Ansible for automated configuration management.

10. Set up Prometheus and Grafana for monitoring.

## Contributors
- The Source code was used by 7 Students and tested. forked from MohamedAliGatri.
- Each of the 7 Students was responsible for his own ci/cd pipeline & DevOps process.

## License
This project is licensed under the [MIT License](LICENSE).

