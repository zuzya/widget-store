#!/bin/bash

appname=widget-store
image=${appname}:1

#build app
mvn clean install -DskipTests=true

#build docker
docker build --force-rm -t "$image" -f ./Dockerfile .
docker run --rm --name ${appname} ${image}