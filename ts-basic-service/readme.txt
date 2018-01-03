
build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name basic-mongo mongo
cd target
docker build -t my/ts-basic-service .
docker run -d -p 15680:15680  --name ts-basic-service --link basic-mongo:basic-mongo my/ts-basic-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-basic-service