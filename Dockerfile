FROM openjdk:8

COPY ./build/libs/sunbook-0.0.1-SNAPSHOT.jar /usr/src/app/

WORKDIR /usr/src/app

CMD java -jar /usr/src/app/sunbook-0.0.1-SNAPSHOT.jar