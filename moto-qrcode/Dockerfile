# ============================
# STAGE 1: BUILD (MAVEN + JDK)
# ============================
FROM maven:3.9-eclipse-temurin-21 AS build

# Pasta de trabalho dentro da imagem
WORKDIR /app

# Copia apenas o pom primeiro (cache de dependências)
COPY pom.xml .

# Baixa dependências (sem compilar o projeto ainda)
RUN mvn -B -q dependency:go-offline

# Agora copia o código-fonte
COPY src ./src

# Gera o JAR (sem rodar testes pra ficar mais rápido)
RUN mvn -B clean package -DskipTests

# ============================
# STAGE 2: RUNTIME (JRE)
# ============================
FROM eclipse-temurin:21-jre-alpine

# Pasta onde o app vai rodar
WORKDIR /app

# Copia o JAR gerado no stage de build
# 👉 CONFERE SE O NOME DO JAR ESTÁ IGUAL AO QUE O MAVEN GERA
COPY --from=build /app/target/moto-qrcode-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Variáveis de ambiente (pode sobrescrever no Render depois)
ENV JAVA_OPTS=""

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
