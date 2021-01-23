#!/bin/bash
# Any subsequent(*) commands which fail will cause the shell script to exit immediately
set -eou

registry=zuzyadocker
appname="widget-store"
version="$1"

docker build --force-rm -t "$appname":"$version" .
docker tag "$appname":"$version" $registry/"$appname":"$version"
docker push $registry/"$appname":"$version"