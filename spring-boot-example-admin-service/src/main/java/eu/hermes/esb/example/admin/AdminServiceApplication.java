package eu.hermes.esb.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.config.EnableAdminServer;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@EnableEurekaClient
public class AdminServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminServiceApplication.class, args);
  }

}
