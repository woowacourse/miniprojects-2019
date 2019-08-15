docker build -t wootube-view .

cd backend
docker build -t wootube \
    --build-arg JAR_NAME=$JAR_NAME \
    --build-arg JAR_PATH=./build/libs/$JAR_NAME .
