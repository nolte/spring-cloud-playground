# Simple Spring Boot Cloud Playground

 Some Playground Project for Testing Spring Cloud Project. 
 
 Used Projects:
  - [Spring Boot Admin][springbootcloudadmin]
  - [Spring Cloud Config][springbootcloudconfig]
  - [Spring Cloud Netflix (Eureka)][springbootcloudeureka]
  - [Apache Camel][springbootapachecamel]  


## Build without Maven On HostSystem

 When you didn`t have istalled Maven, or you dont will download the Dependencies to your local Maven Repo you can build the Project in a Docker Env, based on [maven:3.5.2-jdk-8-alpine|https://hub.docker.com/_/maven/].

### Only Build the Container

```
docker build -t spring-boot-example-build-container . 

docker run -it  --rm  --name spring-boot-example-build \
    -v /var/run/docker.sock:/var/run/docker.sock \
    spring-boot-example-build-container 
```


### Generates jars to Host
```
docker run -it --rm --name spring-boot-example-build-container \
    -v "$PWD":/usr/src/mymaven \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -w /usr/src/mymaven maven:3.5.2-jdk-8-alpine \
    mvn clean install -PwithDocker
```
  
[springbootcloudadmin]: https://github.com/codecentric/spring-boot-admin
[springbootcloudconfig]: https://cloud.spring.io/spring-cloud-config/
[springbootcloudeureka]: https://cloud.spring.io/spring-cloud-netflix/
[springbootapachecamel]: http://camel.apache.org/spring-boot.html