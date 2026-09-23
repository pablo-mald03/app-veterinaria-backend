# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos solo el pom.xml primero para descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear un usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]