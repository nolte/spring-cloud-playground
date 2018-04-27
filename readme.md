# Simple Spring Boot Cloud Playground

 Some Playground Project for Testing Spring Cloud Project. 
 
 Used Projects:
  - [Spring Boot Admin][springbootcloudadmin]
  - [Spring Cloud Config][springbootcloudconfig]
  - [Spring Cloud Netflix (Eureka)][springbootcloudeureka]
  - [Netflix Zuul Api Gateway][netflixzuul]
  - [Zipkin][zipkin]
  - [Apache Camel][springbootapachecamel]  
  - [microservices dashboard][microservicesdashboardserver]


## Build without Maven On HostSystem

 When you didn`t have istalled Maven, or you dont will download the Dependencies to your local Maven Repo you can build the Project in a Docker Env, based on [maven:3.5.2-jdk-8-alpine](https://hub.docker.com/_/maven/).

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
### Start the Local Docker Env

```
cd ./doc

docker-compose up -d configservice dashboardstorage && \
 sleep 20 && docker-compose up -d registryservice adminservice gatewayservice dashboardservice && \
 sleep 5 && docker-compose up -d zipkinstorage && \
 sleep 10 && docker-compose up -d zipkinservice && \
 sleep 1 && docker-compose up -d activemqbroker && \
 sleep 5 && docker-compose up -d applicationone applicationtwo
 
```

[zipkin]: https://zipkin.io/
[microservicesdashboardserver]: https://github.com/ordina-jworks/microservices-dashboard-server
[netflixzuul]:https://github.com/spring-cloud/spring-cloud-netflix  
[springbootcloudadmin]: https://github.com/codecentric/spring-boot-admin
[springbootcloudconfig]: https://cloud.spring.io/spring-cloud-config/
[springbootcloudeureka]: https://cloud.spring.io/spring-cloud-netflix/
[springbootapachecamel]: http://camel.apache.org/spring-boot.html
