#!/bin/bash

cd /opt/apps/ && java \
      -Dservice.name="widget-store" \
      ${JVM_ARGS} -jar -Dspring.profiles.active=$PROFILE  app.jar