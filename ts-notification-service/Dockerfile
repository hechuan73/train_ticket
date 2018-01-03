FROM java:8-jre
MAINTAINER Chenjie Xu <15212010021@fudan.edu.cn>

ADD ./target/ts-notification-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-notification-service-1.0.jar"]

EXPOSE 17853