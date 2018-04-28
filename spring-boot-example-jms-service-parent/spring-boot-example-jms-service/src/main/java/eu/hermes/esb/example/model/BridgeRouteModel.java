package eu.hermes.esb.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BridgeRouteModel extends RouteModel {
	String entry;
	String exit;
}
