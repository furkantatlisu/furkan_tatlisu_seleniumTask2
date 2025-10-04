# Base image: Maven + JDK 17
FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Testleri çalıştır
CMD ["mvn", "test"]