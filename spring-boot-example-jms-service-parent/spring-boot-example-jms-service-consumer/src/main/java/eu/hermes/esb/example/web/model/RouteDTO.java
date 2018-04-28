package eu.hermes.esb.example.web.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "RouteDTO", description = "abstract Route Object", discriminator = "type", subTypes = {
		ConsumingRouteDTO.class, ProducingRouteDTO.class, BridgingRouteDTO.class })
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = ProducingRouteDTO.class, name = "producing"),
		@JsonSubTypes.Type(value = ConsumingRouteDTO.class, name = "consuming"),
		@JsonSubTypes.Type(value = BridgingRouteDTO.class, name = "bridge") })
@AllArgsConstructor
@NoArgsConstructor
public abstract class RouteDTO {

	@ApiModelProperty(example = "jms:queue:example", required = true)
	@NotNull
	private String uri;

	@ApiModelProperty(example = "simple", required = false)
	private String id;

	@ApiModelProperty(example = "false", required = false, notes = "set the created route to transacted mode")
	private boolean transacted = true;

}
