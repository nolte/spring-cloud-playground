package eu.hermes.esb.example.web.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "BridgingRouteDTO", description = "Bridge Messages From Uri to target", parent = RouteDTO.class)
@AllArgsConstructor
@NoArgsConstructor
public class BridgingRouteDTO extends RouteDTO {

	@ApiModelProperty(example = "jms:queue:bridged", required = true)
	@NotNull
	private String bridgeTarget;

	@Builder
	public BridgingRouteDTO(String uri, String id, boolean transacted, String bridgeTarget) {
		super(uri, id, transacted);
		this.bridgeTarget = bridgeTarget;
	}

}
