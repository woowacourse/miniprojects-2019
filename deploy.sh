#!/bin/bash

REPOSITORY=/home/ubuntu/git

cd $REPOSITORY/miniprojects-2019/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew clean build

echo "> Build 파일 복사"

cp ./build/libs/*.jar $REPOSITORY/miniprojects-2019/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f sunbook-0.0.1)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls $REPOSITORY/miniprojects-2019/ -alt | grep 'myblog' | head -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/miniprojects-2019/$JAR_NAME &
