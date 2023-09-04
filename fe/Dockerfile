# Node.js 환경 구성
FROM node:16 as web

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

# nginx 환경 구성
FROM nginx:latest

# web build 결과물 & nginx.conf 복사
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=web /app/dist /usr/share/nginx/html

# 실행 명령어
CMD ["nginx", "-g", "daemon off;"]
