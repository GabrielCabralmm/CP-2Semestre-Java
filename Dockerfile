# ---------- Etapa de build ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Etapa de execução ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/mercado-express.jar app.jar

# A porta 8082 é a definida em application.properties (server.port).
# No Render, o serviço detecta a porta automaticamente pela variável PORT,
# então também expomos a leitura dessa variável (ver observação no README).
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
