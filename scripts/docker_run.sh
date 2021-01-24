#!/bin/bash

appname=widget-store
image=zuzyadocker/${appname}:1

docker pull ${image}
docker run --rm --name ${appname} ${image}