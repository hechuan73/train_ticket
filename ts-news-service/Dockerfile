# FROM grayzone/beego
FROM mrrm/web.go
MAINTAINER Chao Ji <17212010013@fudan.edu.cn>

RUN mkdir -p /app
WORKDIR /app

ADD ./src/main/main.go /app/
RUN go build
CMD [ "./app" ]

EXPOSE 12862