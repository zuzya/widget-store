#!/bin/bash
# Any subsequent(*) commands which fail will cause the shell script to exit immediately
set -eou

echo checking script location
DIR=$(cd "$(dirname "$0")"; pwd -P)
echo script located in $DIR
echo changing dir to $DIR
cd $DIR

registry=zuzyadocker
appname="widget-store"
version="$1"

cd ../
mvn clean package -DskipTests=true

docker build --force-rm -t "$appname":"$version" -f Dockerfile .
docker tag "$appname":"$version" $registry/"$appname":"$version"
docker push $registry/"$appname":"$version"