package eu.hermes.esb.example.frontend.model;

import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {

//	@NotEmpty
//	@Min(1)
	private List<String> destinationServices;

//	@NotEmpty
	private MessageModel message;

}
