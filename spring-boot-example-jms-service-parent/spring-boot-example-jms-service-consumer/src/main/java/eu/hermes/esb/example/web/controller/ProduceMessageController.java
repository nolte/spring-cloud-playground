package eu.hermes.esb.example.web.controller;

import javax.validation.Valid;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.hermes.esb.example.web.model.MessageDTO;
import feign.Headers;
import feign.RequestLine;

@FeignClient("${example-fergin-app-name:example-jms-service}")
public interface ProduceMessageController {

	@RequestLine("POST " + "/api/message")
    @Headers("Content-Type: application/json")
	public boolean sendMessage(@Valid @RequestBody MessageDTO message);

}
