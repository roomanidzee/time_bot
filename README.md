# time_bot

[![Bot CI](https://github.com/roomanidzee/time_bot/actions/workflows/main.yml/badge.svg)](https://github.com/roomanidzee/time_bot/actions/workflows/main.yml)

Telegram bot for time recording

## Technical requirements
- JDK 11
- Maven
- Kotlin 1.6.10
- PostgreSQL
- Telegram Bot API Token


## Bot location in Telegram
https://t.me/time_recording_bot

Bot may be not stable because of Heroku((. But it is being restarted after some time

## Tests
```mvn clean test```

## Package
```mvn clean package```

## Launch
```shell
java -Dserver.port=$PORT $JAVA_TOOL_OPTIONS -jar target/time_bot.jar --server.port=$PORT
```

OR

```shell
docker build -f docker/Dockerfile -t time_bot:latest .
docker-compose -f docker/docker-compose.yml up -d
```
