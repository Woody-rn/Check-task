#build project
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY task-scanner/build.gradle task-scanner/

# Устанавливаем dos2unix и конвертируем переносы строк
RUN apk add --no-cache dos2unix && \
    dos2unix gradlew && \
    chmod +x gradlew
RUN ./gradlew dependencies --no-daemon
COPY task-scanner/src task-scanner/src
RUN ./gradlew :task-scanner:shadowJar --no-daemon

#run project
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/task-scanner/build/libs/*.jar task-scanner.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "task-scanner.jar"]