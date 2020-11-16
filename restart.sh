#!/usr/bin/env bash

docker-compose down
docker rmi $(docker images reit -q) -f
docker build -t reit:latest .
docker-compose up