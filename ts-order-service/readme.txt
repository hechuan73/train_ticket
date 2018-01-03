
rest url:
http://order-query-service:14221/welcome
return String:
"Welcome to [ Order Query Service ] !"

build:
mvn clean package

docker:
docker build -t my/order-query-service .
docker run -d -p 12031:12031 --name ts-order-service --link order-mongo:order-mongo --link ts-sso-service:ts-sso-service my/ts-order-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	account-query-service

