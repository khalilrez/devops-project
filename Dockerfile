FROM openjdk:21-jdk-slim
WORKDIR /back
COPY /target/achat-*.jar achat.jar
EXPOSE 8089
CMD [ "java", "-jar", "achat.jar" ]