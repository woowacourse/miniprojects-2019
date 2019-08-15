#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker tag wootube ${DOCKER_USERNAME}/wootube:$1
docker push ${DOCKER_USERNAME}/wootube:$1

docker tag wootube-view ${DOCKER_USERNAME}/wootube-view:$1
docker push ${DOCKER_USERNAME}/wootube-view:$1