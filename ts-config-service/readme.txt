
build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name config-mongo mongo
cd target
docker build -t my/ts-config-service .
docker run -d -p 15679:15679  --name ts-config-service --link config-mongo:config-mongo my/ts-config-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	verificationCode-login.service