package eu.hermes.esb.example.frontend.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import eu.hermes.esb.example.frontend.FrontendServiceApplication;
import eu.hermes.esb.example.frontend.model.ServiceModel;
import eu.hermes.esb.example.frontend.services.WorkerLocateService;
import eu.hermes.esb.example.web.controller.ProduceMessageController;
import eu.hermes.esb.example.web.controller.RouteController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
public class WelcomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProduceMessageController message;

	@MockBean
	WorkerLocateService locateService;

	@MockBean
	RouteController routes;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/welcome")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Test Service")));
	}

	@Before
	public void mockSomeBehavior() {
		Mockito.doReturn(true).when(message).sendMessage(null);
		Mockito.doReturn(Arrays.asList("a", "b")).when(routes).allExistingRoutes();
		Mockito.doReturn(Arrays.asList(ServiceModel.builder().serviceId("Test Service").build())).when(locateService)
				.loadAllRoutes();
	}
}
