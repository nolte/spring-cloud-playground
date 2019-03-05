FROM maven:3.6.0-jdk-11

COPY . /usr/src/app
WORKDIR /usr/src/app 

ENTRYPOINT ["mvn", "clean", "install","-PwithDocker","-DskipTests=true"]
