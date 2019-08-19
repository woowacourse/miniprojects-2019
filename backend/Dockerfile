FROM openjdk:8

ENV JAR_NAME edd-0.0.1-SNAPSHOT.jar

RUN apt-get update && apt-get install netcat-openbsd -y

COPY . /usr/src/app/build
WORKDIR /usr/src/app/build
RUN ./gradlew --parallel build
RUN mv ./build/libs/$JAR_NAME /usr/src/app
RUN mv ./docker-entrypoint.sh /usr/src/app

WORKDIR /usr/src/app
RUN rm -rf build

CMD ./docker-entrypoint.sh
EXPOSE 8080
