#!/bin/bash

cat .env
set -o allexport; source .env; set +o allexport