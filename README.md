# Check-task

> **Статус:** В активной разработке

Проект для управления задачами с возможностью автоматического импорта из файловой системы.  
Состоит из двух микросервисов: веб-интерфейса для работы с задачами и сервиса-сканера директорий.

## Функциональность
- **Сервис списка задач (task-todo-list):** Веб-интерфейс для просмотра, добавления, отслеживания задач
- **Сервис-сканер (task-scanner):** Автоматическое сканирование директории и поиск файлов с задачами по заданным критериям, сохранение в базу данных.

## Технологии
- **Язык:** Java
- **Фреймворки:** Spring Boot, Javalin, JOOQ
- **БД:** PostgreSQL, миграции через Liquibase
- **Инфраструктура:** Docker Compose, Nginx

## Быстрый старт
1.  Клонируйте репозиторий.
2.  Запустите
    ```bash
    docker compose up -d
    ```
## Доступ к сервисам

После запуска приложения сервисы доступны по следующим URL:

| Сервис | URL | Описание                                                                                |
|--------|-----|-----------------------------------------------------------------------------------------|
| **Todo List** | `http://localhost/` | Основной веб-интерфейс для управления задачами (создание, чтение, обновление, удаление) |
| **Task Scanner** | `http://localhost/scan/` | Веб-интерфейс сервиса автоматической проверки задач                                     |

### Прямой доступ к сервисам (без Nginx)

Если нужно обратиться к сервисам напрямую (например, при отладке):

| Сервис | URL |
|--------|-----|
| Todo List | `http://localhost:8080` |
| Task Scanner | `http://localhost:7000` |

## Список дополнительных команд
```
docker compose -f task-scanner/docker-compose.yaml up  
docker compose up -d postgres liquibase 
docker compose -f ./task-scanner/docker-compose.yaml up 

docker build -t task-todo-list -f task-todo-list/Dockerfile .  
docker run -d -p 8080:8080 --name my-task-todo-list task-todo-list

docker build -t task-scanner -f task-scanner/Dockerfile .  
docker run -d -p 7000:7000 --name task-scanner-container task-scanner  
```
