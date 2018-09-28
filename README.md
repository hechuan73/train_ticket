This project is ticket seller application in microservice architecture, including 40+ microservices
=========================

- java，spring boot，spring cloud
- nodejs，express
- python，dijango
- go，webgo

---
## Supported Cluster Orchestration
- K8S + Istio
- Docker Swarm

##  Local runtime environment
- docker
- docker-compose
- k8s
- maven
- jdk8

## Deploy Train Ticket with docker-compose

      you jsut need one machine and installed with  git, jdk8, maven, docker and docker-compose
      setup with the following steps:
     (1) Clone all the source code to your local computer with git.
     (2) Move all directory whose name is start with "ts-" to your server's  other directory. 
     (3) Move pom.xml and docker-compose.xml to your server to the same directory with "ts-..." directories.
     (4) Add the "myproject" directory to your maven ".m2" directory.  The directory index is like  /root/.m2/repository/myproject.
     (5) Open the terminal and enter the directory where pom.xml loacted.   
     (6) Use the instruction: mvn clean package  And waiting for build success.   
     (7) Use the instruction: docker-compose build  And waiting for build success.
     (8) Use the instruction: docker-compose up   And wating for all services startup
     
## Access the Train Ticket System in Browser 
    before access, you can use docker ps to see which port you should access:
    on default. the port is 80
    than you can use the ip of your server to access it.
    Unless something unexpected, then you can see the main interface
    ![main interface](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/main_interface.png)
    
##  The Step of Buy Ticket
    (1) while access the ui , then you can    
     
    