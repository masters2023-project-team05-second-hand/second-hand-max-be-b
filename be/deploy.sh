#!/bin/bash

# 저장소 업데이트
echo "저장소 업데이트"
git fetch origin stage

REMOTE=`git rev-parse origin/stage`
LOCAL=`git rev-parse HEAD`

if [[ $REMOTE = $LOCAL ]]; then
        echo "저장소 내용 동일. 빌드하지 않고 종료."
        exit 0
fi

git merge origin/stage

# 실행중인 jar 확인
APP_ID=`jps | grep second-hand | awk '{ print $1 }'`

if [ -z $APP_ID ]; then
        echo "실행중인 jar가 없습니다."
else
        echo "$APP_ID 프로세스를 종료합니다."
        kill -9 $APP_ID
fi

# build
echo "빌드 시작"
rm -rf build
chmod +x ./gradlew
./gradlew clean build -x test

# [참고] JASYPT_PASSWORD는 서버 내 ./bash_profile에 설정되어 있음
echo "서버 시작"
nohup java -jar -Djasypt.encryptor.password=$JASYPT_PASSWORD ~/app/second-hand-max-be-b/be/build/libs/second-hand-0.0.1-SNAPSHOT.jar > ~/app/log.txt 2>&1 &
echo "배포 완료!"
