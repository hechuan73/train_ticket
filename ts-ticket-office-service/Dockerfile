FROM node
MAINTAINER dingding <17212010004@fudan.edu.cn>

RUN mkdir -p /app
WORKDIR /app

ADD ./node_modules /app/node_modules
ADD ./package.json /app/
ADD ./public /app/public
# RUN npm install

ADD ./bin /app/bin
ADD ./app.js /app/
CMD [ "npm", "start" ]

EXPOSE 16108