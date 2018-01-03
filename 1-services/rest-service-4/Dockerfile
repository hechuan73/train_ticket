FROM java:8-jre
MAINTAINER jay <15110240026@fudan.edu.cn>

ADD ./target/rest-service-4-0.1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/rest-service-4-0.1.0.jar"]

EXPOSE 16004