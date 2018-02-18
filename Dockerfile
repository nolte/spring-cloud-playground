FROM maven:3.5.2-jdk-8-alpine

COPY . /usr/src/app
WORKDIR /usr/src/app 

ENTRYPOINT ["mvn", "clean", "install", "-PwithDocker"]
