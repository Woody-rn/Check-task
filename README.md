```
docker compose -f task-scanner/docker-compose.yaml up  
docker compose up -d postgres liquibase 
docker compose -f ./task-scanner/docker-compose.yaml up 

docker build -t task-todo-list -f task-todo-list/Dockerfile .  
docker run -d -p 8080:8080 --name my-task-todo-list task-todo-list

docker build -t task-scanner -f task-scanner/Dockerfile .  
docker run -d -p 7000:7000 --name task-scanner-container task-scanner  
```