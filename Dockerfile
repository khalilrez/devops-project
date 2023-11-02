FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/devops-project.jar devops-project.jar
ENTRYPOINT ["java","-jar","/devops-project.jar"]