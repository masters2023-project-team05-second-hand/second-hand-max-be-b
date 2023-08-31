# Node.js 환경 구성
FROM node:16

# 작업 디렉토리 지정
WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 프로젝트 의존성 설치
RUN npm install

# React 앱 소스 복사
COPY . .

# 빌드
RUN npm run build
