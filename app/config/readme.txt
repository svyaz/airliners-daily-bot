vi/vim на Debian:
    Если не работает, то:
    - apt-get update
    - apt-get install vim

docker на хостинге:
    - скопировать приложение на хостинг:
      scp ~/IdeaProjects/AirlinersDailyBot/app/build/libs/app-airliners-daily-bot-1.0.1-boot.jar root@<IP>:/root/app/
    - установить в переменную окружения BOT_TOKEN значение токена!
    - установить в конфиге application-prod.yaml значение пароля к БД!
    - создать образ из Dockerfile:
        docker build -t airliners-bot-app-1:latest .

Создаем контейнер с приложением:
    - Если нужно установить еще какой-то docker-контейнер, то исправить файл docker-app.yml, который там лежит.
    - запустить:
        docker-compose -f docker-app.yml -p airliners-bot up -d