package eu.hermes.esb.example.registry;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

  public static void main(String[] args) {
	  GracefulshutdownSpringApplication.run(ServiceRegistryApplication.class, args);
  }

}
