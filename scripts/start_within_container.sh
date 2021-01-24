#!/bin/bash

cd /opt/apps/ && java \
      -Dservice.name=${APP_NAME} \
      ${JVM_ARGS} -jar -Dspring.profiles.active=$PROFILE  app.jar