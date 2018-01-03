FROM java:8-jre
MAINTAINER Wenhai Li<16212010016@fudan.edu.cn>

ADD ./target/ts-consign-price-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-consign-price-service-1.0.jar"]

EXPOSE 16110