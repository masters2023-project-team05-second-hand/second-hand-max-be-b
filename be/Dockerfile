# open jdk 11 버전의 환경 구성
FROM adoptopenjdk/openjdk11:latest

# 이미지 내부 작업 디렉토리 지정
WORKDIR /app

# build가 되는 시점에 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
ARG JAR_FILE=./build/libs/second-hand-0.0.1-SNAPSHOT.jar

# JAR_FILE을 이미지에 복사
COPY ${JAR_FILE} second-hand.jar

# 컨테이너가 실행될 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "-Djasypt.encryptor.password=${JASYPT_PASSWORD}", "-Dspring.profiles.active=prod", "/app/second-hand.jar"]
