package eu.hermes.esb.example.web.model;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@ApiModel(value = "message", description = "Present a Produced Message")
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

	@ApiModelProperty(example = "jms:queue:example", required = true)
	@NotNull
	private String destinationUri;

	@ApiModelProperty(required = false)
	@Singular
	private Map<String, Object> headers;

	@ApiModelProperty(example = "hallo world", required = true)
	@NotNull
	@Size(min = 2, message = "The message should have atleast 2 characters")
	private String payload;

}
