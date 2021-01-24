#!/bin/bash
# Any subsequent(*) commands which fail will cause the shell script to exit immediately
set -eou

registry=zuzyadocker
appname="widget-store"
version="$1"

mvn clean install -DskipTests=true

docker build --force-rm -t "$appname":"$version" -f ./Dockerfile .
docker tag "$appname":"$version" $registry/"$appname":"$version"
docker push $registry/"$appname":"$version"