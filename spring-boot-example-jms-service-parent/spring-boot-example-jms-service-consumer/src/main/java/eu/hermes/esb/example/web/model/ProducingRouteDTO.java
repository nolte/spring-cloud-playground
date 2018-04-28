package eu.hermes.esb.example.web.model;

import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "ProducingRouteDTO", description = "Produce message by given Cron with additional Header", parent = RouteDTO.class)
@AllArgsConstructor
@NoArgsConstructor
public class ProducingRouteDTO extends RouteDTO {

	@ApiModelProperty(required = false)
	private Map<String, String> headers;

	@ApiModelProperty(example = "200", required = true)
	@NotNull
	private int producedSize = 100;

	@ApiModelProperty(example = "0/10+*+*+*+*+?", required = true)
	@NotNull
	private String cron;

	@Builder
	public ProducingRouteDTO(String uri, String id, boolean transacted, Map<String, String> headers, int producedSize,
			String cron) {
		super(uri, id, transacted);
		this.headers = headers;
		this.producedSize = producedSize;
		this.cron = cron;
	}

}
