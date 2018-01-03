
rest url:
http://verificationCode
return an image which contains verification code,like:
2468,which is an image

build:
mvn clean package

docker:
docker build -t my/ts-verification-code-service .
docker run -d -p 15678:15678 --name ts-verification-code-service my/ts-verification-code-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-verification-code-service