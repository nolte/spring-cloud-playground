package eu.hermes.esb.example.zipkin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
@EnableEurekaClient
@EnableAutoConfiguration
public class ZipkinServiceApplication {

	public static void main(String[] args) {
		GracefulshutdownSpringApplication.run(ZipkinServiceApplication.class, args);
	}

}
