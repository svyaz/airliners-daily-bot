# Используем официальный образ OpenJDK
FROM openjdk:21-jdk-slim

# Создаем рабочую директорию
WORKDIR /app

# Создаем каталоги
RUN mkdir -p /app/logs && chmod 755 /app/logs
RUN mkdir -p /app/config && chmod 755 /app/config

# Копируем файлы приложения
COPY app/airliners-daily-bot.jar /app/airliners-daily-bot.jar
COPY app/config/application-prod.yaml /app/config/application-prod.yaml
COPY app/config/logback.xml /app/config/logback.xml

# Устанавливаем переменные окружения
ENV BOT_TOKEN=XXX
ENV JAVA_OPTS="-Xms1024m -Xmx1024m"
ENV APP_OPTS="--spring.profiles.active=prod --logging.config=config/logback.xml"

# Команда для запуска приложения
CMD ["sh", "-c", "java ${JAVA_OPTS} -jar airliners-daily-bot.jar ${APP_OPTS}"]

# Можно указать порт, если приложение использует его
# EXPOSE 8080

