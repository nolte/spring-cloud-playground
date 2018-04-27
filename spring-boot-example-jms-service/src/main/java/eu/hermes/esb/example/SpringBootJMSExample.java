package eu.hermes.esb.example;

import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
@EnableJms
@EnableTransactionManagement
@CamelZipkin
public class SpringBootJMSExample {

  public static void main(String[] args) throws Exception {
	  GracefulshutdownSpringApplication.run(SpringBootJMSExample.class, args);
  }

}
