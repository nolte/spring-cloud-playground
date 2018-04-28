package eu.hermes.esb.example.frontend.model;

import java.net.URI;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceModel {

	private String serviceId;

	private URI uri;

	@Singular
	List<String> routes;

}
