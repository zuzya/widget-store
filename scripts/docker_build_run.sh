#!/bin/bash

echo checking script location
DIR=$(cd "$(dirname "$0")"; pwd -P)
echo script located in $DIR
echo changing dir to $DIR
cd $DIR

appname=widget-store
image=${appname}:1

cd ../
#build app
mvn clean package -DskipTests=true

#build docker
docker build --force-rm -t "$image" -f Dockerfile .
docker run --rm --name ${appname} --env-file .env  ${image}