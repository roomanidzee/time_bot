#!/usr/bin/env bash

cmd="$*"

if [ "$1" = 'start' ]; then
    /opt/work/bot/bin/bot.sh start
    sleep 20
    tail -f /opt/work/bot/logs/bot.log
elif [ "$1" = 'restart' ]; then
    /opt/work/bot/bin/bot.sh restart
    sleep 20
    tail -f /opt/work/bot/logs/bot.log
elif [ "$1" = 'stop' ]; then
    /opt/work/bot/bin/bot.sh stop
else
    exec "$cmd"
fi