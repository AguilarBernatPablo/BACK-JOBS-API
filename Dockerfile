# 1. Compilar la aplicación usando Maven con Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Ejecutar el jar compilado usando Java 21 (Imagen ligera alpine)
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /target/jobs-0.0.1-SNAPSHOT.jar jobs.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jobs.jar"]
