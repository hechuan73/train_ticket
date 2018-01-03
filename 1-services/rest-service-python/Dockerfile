FROM erkekin/tornado
MAINTAINER jay <15110240026@fudan.edu.cn>

RUN mkdir -p /app
WORKDIR /app

# ADD ./node_modules /app/node_modules
# ADD ./package.json /app/
# RUN npm install

ADD ./server.py /app/
CMD [ "python", "server.py" ]

EXPOSE 16101