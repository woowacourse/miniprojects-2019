#!/usr/bin/env bash

kill $(lsof -t -i:8080)
git pull
./gradlew clean build
nohup java -jar ./build/libs/$(ls ./build/libs) &