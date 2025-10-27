FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar prueba.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "prueba.jar"]