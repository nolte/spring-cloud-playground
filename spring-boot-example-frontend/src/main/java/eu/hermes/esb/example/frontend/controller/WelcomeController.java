package eu.hermes.esb.example.frontend.controller;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import eu.hermes.esb.example.frontend.model.MessageForm;
import eu.hermes.esb.example.frontend.model.MessageModel;
import eu.hermes.esb.example.frontend.model.ServiceModel;
import eu.hermes.esb.example.frontend.services.WorkerLocateService;
import eu.hermes.esb.example.web.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WelcomeController {

	@Autowired
	WorkerLocateService locateService;

	@GetMapping("/")
	public String greeting(Model model) {
		return "pages/index";
	}

	@GetMapping("/welcome")
	public String welcome(Model model) {
		List<ServiceModel> services = locateService.loadAllRoutes();
		model.addAttribute("services", services);
		return "pages/welcome";
	}

	@GetMapping("/produce")
	public String produce(MessageForm messageForm, Model model) {
		model.addAttribute("services", locateService.loadRouteControllingSerivces());
		return "pages/produce";
	}

	@PostMapping("/produce")
	public String checkPersonInfo(@Valid MessageForm messageForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "pages/produce";
		}

		for (String destinationService : messageForm.getDestinationServices()) {
			MessageModel message = messageForm.getMessage();
			MessageDTO map = MessageDTO.builder().destinationUri(message.getDestinationUri()).payload(message.getPayload()).build();
			locateService.sendMessageOverService(destinationService,
					map);
		}

		return "redirect:/welcome";
	}
}
