#!/bin/bash

while ! nc -z db 3306; do
    >& 2
    sleep 5
done

java -jar $JAR_NAME