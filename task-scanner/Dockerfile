#build project
FROM gradle:9.3.1-jdk21-alpine AS builder
WORKDIR /app
COPY gradle/libs.versions.toml gradle/
COPY build.gradle settings.gradle ./
COPY task-scanner/build.gradle task-scanner/

RUN gradle dependencies --no-daemon
COPY task-scanner/src task-scanner/src
RUN gradle :task-scanner:shadowJar --no-daemon

#run project
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/task-scanner/build/libs/*.jar task-scanner.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "task-scanner.jar"]