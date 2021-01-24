#!/bin/bash

echo checking script location
DIR=$(cd "$(dirname "$0")"; pwd -P)
echo script located in $DIR
echo changing dir to $DIR
cd $DIR

sh load_env.sh
cd ../

echo running spring boot
mvn spring-boot:run  -Dspring-boot.run.profiles=$PROFILE