package eu.hermes.esb.example.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BridgeRouteModel extends RouteModel {
	private String entry;
	private String exit;
}
