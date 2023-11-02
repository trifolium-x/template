FROM openjdk:17

MAINTAINER trifolium <trifolium.wang@foxmail.com>

LABEL app="template-service" version="1.0-SNAPSHOT" by="trifolium"

ENV APP_VERSION 1.0-SNAPSHOT

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir /app \
    && mkdir /app/logs

WORKDIR /app

COPY services/template-service/target/template-service.jar /app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080