FROM python:3
MAINTAINER Li Wenhai <16212010016@fudan.edu.cn>

RUN mkdir -p /app
WORKDIR /app

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

ADD ./server.py /app/
CMD [ "python", "server.py" ]

EXPOSE 16101