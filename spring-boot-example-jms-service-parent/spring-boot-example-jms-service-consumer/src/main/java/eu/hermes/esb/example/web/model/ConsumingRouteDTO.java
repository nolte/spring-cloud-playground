package eu.hermes.esb.example.web.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "ConsumingRouteDTO", description = "Consumes messages from given uri", parent = RouteDTO.class)
@NoArgsConstructor
public class ConsumingRouteDTO extends RouteDTO {
	@Builder
	public ConsumingRouteDTO(String uri, String id, boolean transacted) {
		super(uri, id, transacted);
	}

}
