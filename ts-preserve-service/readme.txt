
rest url:
http://train

build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name preserve-mongo mongo
cd target
docker build -t my/ts-preserve-service .
docker run -d -p 14568:14568  --name ts-preserve-service --link preserve-mongo:preserve-mongo my/ts-preserve-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-preserve-service