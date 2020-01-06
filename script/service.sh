#!/bin/sh
#
### BEGIN INIT INFO
# Provides:          <NAME>
# Required-Start:    <REQUIRED_START>
# Required-Stop:     <REQUIRED_STOP>
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Description:       <DESCRIPTION>
### END INIT INFO

NAME=<NAME>
PATH_TO_JAR=<PATH_TO_JAR>
JAR_NAME=$NAME.jar
PIDFILE=/tmp/$NAME.pid

start() {
  if [ -f $PIDFILE ] && [ -s $PIDFILE ] && kill -0 $(cat $PIDFILE); then
    echo 'Service already running' >&2
    return 1
  fi
  echo 'Starting service…' >&2

  cd $PATH_TO_JAR
  nohup java -server \
              -Dpatriot.env=<ENVIRONMENT> \
              -Duser.timezone=<TIMEZONE> \
              -Dfile.encoding=UTF-8 \
              -jar $JAR_NAME /tmp 2>> /dev/null >> /dev/null &
                    echo $! > $PIDFILE

  sleep 2
  PID=$(cat $PIDFILE)

  if [ -f $PIDFILE ]; then
    echo "$NAME is now running, the PID is $PID"
  else
    echo ''
    echo "Error! Could not start $NAME!"

    return 1
  fi

  return 0
}

stop() {
  if [ ! -f "$PIDFILE" ] || ! kill -0 $(cat "$PIDFILE"); then
    echo 'Service not running' >&2
    return 1
  fi
  echo 'Stopping service…' >&2
  kill -15 $(cat "$PIDFILE") && rm -f "$PIDFILE"
  echo 'Service stopped' >&2

  return 0
}

status() {
    printf "%-50s" "Checking <NAME>..."
    if [ -f $PIDFILE ] && [ -s $PIDFILE ]; then
        PID=$(cat $PIDFILE)
            if [ -z "$(ps axf | grep ${PID} | grep -v grep)" ]; then
                printf "%s\n" "The process appears to be dead but pidfile still exists"
            else    
                echo "Running, the PID is $PID"
            fi
    else
        printf "%s\n" "Service not running"
    fi

    return 0
}


case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  status)
    status
    ;;
  restart)
    stop
    start
    ;;
  *)
    echo "Usage: $0 {start|stop|status|restart}"
esac
