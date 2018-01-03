
rest url:
http://ts-price-service:12345/station

build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name station-mongo mongo
cd target
docker build -t my/ts-station-service .
docker run -d -p 12345:12345  --name ts-station-service --link station-mongo:station-mongo my/ts-station-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-station-service
