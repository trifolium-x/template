#!/bin/bash
mvn -skipTest=true clean package
finalName="template"
version="latest"
port="8080"
echo "构建镜像:"${finalName}:${version}
docker stop ${finalName}
docker rm ${finalName}
docker rmi ${finalName}:${version}
docker build -f ./Dockerfile --rm -t ${finalName}:${version} .
echo "构建完成."

echo "启动中..."
docker run --name ${finalName} -d -p ${port}:${port} \
--memory-reservation=1024M --memory=1536M --oom-kill-disable=true \
${finalName}:${version} --spring.profiles.active=test

#--network=test-net --ip=172.20.0.6
echo "启动完成."