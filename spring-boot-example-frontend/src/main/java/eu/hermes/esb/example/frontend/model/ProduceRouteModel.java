package eu.hermes.esb.example.frontend.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProduceRouteModel extends RouteModel {
	String cron;
	String exit;
	int producedSize = 100;
	
	@Singular
	List<HeaderModel> additionalHeaders = new ArrayList<HeaderModel>();
}
