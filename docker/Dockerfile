FROM openjdk:11.0-slim-bullseye

RUN apt-get update && \
    apt-get install unzip && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /opt/work/bot

ENV APP_WORK_PATH=/opt/work/bot

COPY ./target/*.zip bot.zip
COPY ./docker/entrypoint.sh /docker-entrypoint.sh
RUN unzip bot.zip && chmod +x ./bin/bot.sh

ENTRYPOINT ["/docker-entrypoint.sh"]