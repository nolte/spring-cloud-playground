package eu.hermes.esb.example.frontend.model;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {
	
//	@NotNull
	private String destinationUri;

	@Singular
	private Map<String, Object> headers;

//	@NotNull
//	@Size(min = 2, message = "The message should have atleast 2 characters")
	private String payload;

}
