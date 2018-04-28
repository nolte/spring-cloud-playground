package eu.hermes.esb.example.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class RouteModel {
	
	private String id;

	private boolean transacted = true;
	
}
