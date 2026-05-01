#build project
FROM gradle:9.3.1-jdk21-alpine AS builder
WORKDIR /app
COPY gradle/libs.versions.toml gradle/
COPY build.gradle settings.gradle ./
COPY task-todo-list/build.gradle task-todo-list/

RUN gradle dependencies --no-daemon
COPY task-todo-list/src task-todo-list/src
RUN gradle :task-todo-list:bootJar --no-daemon

#run project
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/task-todo-list/build/libs/*.jar task-todo-list.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-todo-list.jar"]