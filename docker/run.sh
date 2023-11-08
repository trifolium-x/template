nohup java -jar template.jar --spring.profiles.active=test --spring.datasource.password=xxxxx \
--spring.redis.password=xxxxx --server.port=8080 >/etc/null 2>&1 &