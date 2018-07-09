# Deploy Train Ticket on K8S

## Step 1: Build docker image
    In this step, you will build the docker image. <br>  
    (1) Move all directory whose name is start with "ts-" to your server. <br>  
    (2) Move pom.xml and docker-compose.xml to your server to the same directory with "ts-..." directories. <br>  
    (3) Open the terminal and enter the directory where pom.xml loacted. <br>  
    (4) Use the instruction: mvn clean package. And waiting for build success. <br>  
    (5) Use the instruction: docker-compose build. And waiting for build success.\<br>  
  
     

## Step 2: Upload docker image to docker registry
    In this step, you will upload you docker image to your docker registry. <br>  
    (1) Use the instructions in Part1 in [Docker Tag&Upload.md] to tag your docker images. <br>  
    (2) Use the instructions in Part2 in [Docker Tag&Upload.md] to upload the docker images. <br>  

## Step 3: Deploy on K8S
    In this step, you will deploy the Train-Ticket system on K8S.\<br>  
    (1) Move "ts-deployment-part1.yml", "ts-deployment-part2.yml", "ts-deployment-part3.yml". <br>  
    (2) Use the instrcution "kubectl apply -f ts-deployment-part1.yml" <br>  
    (3) Use the instrcution "kubectl apply -f ts-deployment-part2.yml" <br>  
    (4) Use the instrcution "kubectl apply -f ts-deployment-part3.yml" <br>  

    