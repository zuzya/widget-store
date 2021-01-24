#!/bin/bash

echo exporting env file
cat ./../.env
set -o allexport; source ./../.env; set +o allexport