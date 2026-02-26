FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY task-todo-list/build.gradle task-todo-list/

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon
COPY task-todo-list/src task-todo-list/src
RUN ./gradlew :task-todo-list:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/task-todo-list/build/libs/*.jar task-todo-list.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-todo-list.jar"]