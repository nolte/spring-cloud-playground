package eu.hermes.esb.example.model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Configuration
@ConfigurationProperties("jmsexample.routes")
public class ExampleAppProperties {

  List<BridgeRouteModel> bridge;
  List<ConsumingRouteModel> consuming;
  List<ProduceRouteModel> producing;

}
