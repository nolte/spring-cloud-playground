package eu.hermes.esb.example.frontend.controller;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.hermes.esb.example.frontend.model.BridgeRouteCreateForm;
import eu.hermes.esb.example.frontend.model.ConsumingRouteCreateForm;
import eu.hermes.esb.example.frontend.model.HeaderModel;
import eu.hermes.esb.example.frontend.model.ProduceRouteModel;
import eu.hermes.esb.example.frontend.model.ProducingRouteCreateForm;
import eu.hermes.esb.example.frontend.services.WorkerLocateService;
import eu.hermes.esb.example.web.model.BridgingRouteDTO;
import eu.hermes.esb.example.web.model.ConsumingRouteDTO;
import eu.hermes.esb.example.web.model.ProducingRouteDTO;

@Controller()
@RequestMapping("/create")
@ControllerAdvice
public class RouteCreateController {

	@Autowired
	WorkerLocateService locateService;

	@Autowired
	private Mapper mapper;

	private void appendServicesToModel(Model model) {
		model.addAttribute("services", locateService.loadRouteControllingSerivces());
	}

	@GetMapping("/bridge")
	public String showCreatePage(BridgeRouteCreateForm routeForm, Model model) {
		model.addAttribute("routeForm", routeForm);
		appendServicesToModel(model);
		return "pages/create";
	}

	@PostMapping("/bridge")
	public String checkPersonInfo(@Valid BridgeRouteCreateForm routeForm, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "pages/produce";
		}
		BridgingRouteDTO dto = mapper.map(routeForm.getRouteToCreate(), BridgingRouteDTO.class);
		locateService.createRoute(routeForm.getDestinationServices(), dto);
		return "redirect:/welcome";
	}

	@GetMapping("/producing")
	public String showCreateProducingPage(ProducingRouteCreateForm routeForm, Model model) {
		routeForm.setRouteToCreate(ProduceRouteModel.builder().additionalHeader(HeaderModel.builder().build()).build());
		model.addAttribute("routeForm", routeForm);
		appendServicesToModel(model);
		return "pages/create_producing";
	}

	@PostMapping("/producing")
	public String submitCreateProducingPage(@Valid ProducingRouteCreateForm routeForm, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "pages/produce";
		}
		ProducingRouteDTO dto = mapper.map(routeForm.getRouteToCreate(), ProducingRouteDTO.class);
		locateService.createRoute(routeForm.getDestinationServices(), dto);
		return "redirect:/welcome";
	}

	@GetMapping("/consuming")
	public String showCreateConsumingRoutePage(ConsumingRouteCreateForm routeForm, Model model) {
		model.addAttribute("routeForm", routeForm);
		appendServicesToModel(model);
		return "pages/create_consuming";
	}

	@PostMapping("/consuming")
	public String submitConsumingRoute(@Valid ConsumingRouteCreateForm routeForm, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "pages/produce";
		}
		ConsumingRouteDTO dto = mapper.map(routeForm.getRouteToCreate(), ConsumingRouteDTO.class);
		locateService.createRoute(routeForm.getDestinationServices(), dto);
		return "redirect:/welcome";
	}
}
