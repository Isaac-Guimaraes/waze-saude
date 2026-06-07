# Etapa de compilação
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o ficheiro .jar gerado para a imagem final
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]