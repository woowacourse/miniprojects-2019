FROM openjdk:8

ENV JAR_NAME edd-0.0.1-SNAPSHOT.jar

COPY . /usr/src/app/build
WORKDIR /usr/src/app/build
RUN ./gradlew build
RUN mv ./build/libs/$JAR_NAME /usr/src/app

WORKDIR /usr/src/app
RUN rm -rf build

CMD java -jar $JAR_NAME
