package eu.hermes.esb.example.web.controller;

import static eu.hermes.esb.example.SpringBootTechnicalProfiles.ROUTES_FROM_API;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.hermes.esb.example.web.model.MessageDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/message")
@Profile({ ROUTES_FROM_API })
public class ProduceMessageControllerImpl implements ProduceMessageController {

	@Autowired
	private ProducerTemplate jmsTemp;

	@EndpointInject(uri = "direct:produceJms")
	Endpoint defaultEndpoint;

	@Override
	@ApiOperation(value = "sendMessage", nickname = "sendMessage")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public boolean sendMessage(@Valid @RequestBody MessageDTO message) {

		log.debug("send message");
		Map<String, Object> headers = new HashMap<String, Object>();

		headers.put(JmsConstants.JMS_DESTINATION, message.getDestinationUri());
		if (message.getHeaders() != null)
			headers.putAll(message.getHeaders());

		jmsTemp.setDefaultEndpoint(defaultEndpoint);
		jmsTemp.sendBodyAndHeaders(defaultEndpoint, message.getPayload(), headers);
		return true;
	}

}
