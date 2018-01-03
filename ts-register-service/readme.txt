
rest url:
http://account-register-service:12344/welcome
return String:
"Welcome to [ Account Register Service ] !"

build:
mvn clean package

docker:
docker build -t my/ts-register-service .
docker run -p 12344:12344 --name ts-register-service --link register-mongo:register-mongo --link ts-verification-code-service:ts-verification-code-service  my/ts-register-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-register-service

