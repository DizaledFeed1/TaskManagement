Руководство по установке и запуску

1.Загружаем Docker-образ:
docker pull dizaledfeed/taskmanagement-app:latest

2. Клонируем репозиторий
   
4. Запускаем приложение:
    docker-compose up -d


Руководство пользования 
переходим на http://localhost:8080/register
и регистрируем пользователей (ROLE_EXECUTOR - для исполнителя, ROLE_ADMIN - для админа).
Переходим на http://localhost:8080/login и авторизуемся 

Swagger IU нанодиться: http://localhost:8080/swagger-ui/index.html
