package eu.hermes.esb.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RefreshScope
@Profile({"senderweb"})
public class ProduceMessageController {

  @Autowired
  private ProducerTemplate jmsTemp;

  @RequestMapping(method = RequestMethod.POST)
  public void sendMessage(@RequestBody String destination) {
    log.debug("send message");
    Map<String, Object> headers = new HashMap<String, Object>();
    headers.put(JmsConstants.JMS_DESTINATION, destination);
    jmsTemp.sendBodyAndHeader("direct:produceJms", "test Message", headers);
  }

}
