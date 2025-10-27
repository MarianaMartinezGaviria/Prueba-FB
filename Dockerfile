# Etapa de construcción
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de empaquetado (runtime)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/prueba.jar prueba.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "prueba.jar"]