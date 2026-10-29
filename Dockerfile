# 1. Compilar la aplicación usando Maven (Se mantiene igual)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Ejecutar el jar compilado (Cambiado a una imagen válida de Temurin)
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/jobs-0.0.1-SNAPSHOT.jar jobs.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jobs.jar"]
