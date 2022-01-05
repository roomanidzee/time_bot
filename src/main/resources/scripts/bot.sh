#!/usr/bin/env bash

JAR_PATH=$APP_WORK_PATH/time_bot.jar
APP_NAME_PID=time_bot
PID_PATH=$APP_WORK_PATH/bin/time_bot.pid
CONF_DIR=$APP_WORK_PATH/config
LOG_PATH=$APP_WORK_PATH/logs

retrieve_app_pid() {
  echo ps aux | grep ${APP_NAME_PID} | grep -v grep | awk '{ print $2 }'
}

execute() {

  nohup java \
        -Dlog4j.configurationFile=$CONF_DIR/log4j2.xml \
        -Dlogging.config=$CONF_DIR/log4j2.xml \
        -Dlog4j2.debug \
        $JAVA_OPTS -jar $JAR_PATH &

}

retrieve_app_pid() {
  echo ps aux | grep ${APP_NAME_PID} | grep -v grep | awk '{ print $2 }'
}

start() {

  echo "bot starting ..."

  if [ ! -f $PID_PATH ]; then
    execute

    sleep 2

    app_pid=$(retrieve_app_pid)

    if [ ! -f "${PID_PATH}" ]; then
      echo $app_pid>$PID_PATH
      echo "bot started"
    else
      echo "Launch of script for bot failed. Please, check logs."
      exit 1
    fi

  else
    echo "bot already started"
  fi
}

stop() {

  if [ -f $PID_PATH ]; then
    PID=$(cat $PID_PATH);
    echo "bot stopping..."
    kill -n 9 $PID;
    rm $PID_PATH;
    echo "bot stopped"
  else
    echo "bot is not running"
  fi

}

restart() {

  if [ -f $PID_PATH ]; then
    echo "bot is running"
    stop
    start
  else
    echo "bot is not running"
    start
  fi
}

case $1 in
start)
  start $2
;;
stop)
  stop $2
;;
restart)
  restart $2
;;
esac

