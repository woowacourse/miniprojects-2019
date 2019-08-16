# 우아한테크코스 레벨3 미니 프로젝트 - Wootube

## 설치&실행

### Prerequisite

* (도커 사용시) Docker

* (직접 실행시) JDK 8 or later 
* (직접 실행시) npm



### 도커 사용시

```bash
$ git clone -b develop https://github.com/everyone-driven-development/miniprojects-2019
$ cd miniprojects-2019
$ docker-compose up -d --build
```

브라우저에서 `http://localhost`로 접속할 수 있습니다.



Windows에서 실행 시 다음과 같은 오류가 발생할 수 있습니다:

```
/usr/bin/env: ‘sh\r’: No such file or directory
ERROR: Service 'app' failed to build: The command '/bin/sh -c ./gradlew build' returned a non-zero code: 127
```

이 경우, 다음과 같이 해결할 수 있습니다:

```bash
$ sed $'s/\r$//' backend\gradlew
$ sed -i -e 's/\r$//' backend\docker-entrypoint.sh
$ docker-compose up -d --build
```



### 직접 실행

```bash
$ git clone -b develop https://github.com/everyone-driven-development/miniprojects-2019
$ cd backend
$ ./gradlew build # Windows인 경우 gradlew.bat build
$ java -jar build/libs/edd-0.0.1-SNAPSHOT.jar
```

혹은 IDE에서 backend 프로젝트를 import한 뒤 실행할 수 있습니다.



직접 실행하는 경우 웹 페이지의 API 서버 주소를 다음과 같이 변경해주세요.

**frontend/js/common.js**:

```javascript
// ... wootubeCtx.constants
BASE_URL: 'http://localhost:8080'
```

live-server가 설치되지 않은 경우:

```bash
$ npm install -g live-server
```

프로젝트 루트 디렉터리에서 다음 명령어로 웹 서버를 실행합니다:

```bash
$ live-server frontend --port=8000
```

브라우저에서 `http://localhost:8000`로 접속할 수 있습니다.

