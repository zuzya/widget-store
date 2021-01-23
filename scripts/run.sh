#!/bin/bash

#mvn clean install -DskipTests=true

java -Dservice.name="widget-store" \
     ${JVM_ARGS} -jar -Dspring.profiles.active=$PROFILE ./target/widget-store-0.0.1-SNAPSHOT.jar