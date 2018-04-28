package eu.hermes.esb.example.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProduceRouteModel extends RouteModel {
	String cron;
	String exit;
	int producedSize = 100;
	
	Map<String,String> additionalHeaders;
}
