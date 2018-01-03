
rest url:
http://travel2

build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name travel2-mongo mongo
cd target
docker build -t my/ts-travel-service .
docker run -d -p 16346:16346  --name ts-travel2-service --link travel2-mongo:travel2-mongo my/ts-travel2-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-travel2-service