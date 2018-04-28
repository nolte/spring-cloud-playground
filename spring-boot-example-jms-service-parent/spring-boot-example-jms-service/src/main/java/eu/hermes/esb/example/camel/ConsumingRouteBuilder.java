package eu.hermes.esb.example.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.component.metrics.MetricsConstants;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;

import eu.hermes.esb.example.model.ConsumingRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumingRouteBuilder extends AbstractRouteBuilder<ConsumingRouteModel> {
	public static final String PREFIX = "consuming";

	public ConsumingRouteBuilder(ConsumingRouteModel routeModel) {
		super(routeModel, PREFIX);
	}

	@Override
	protected RouteDefinition withStart() {
		return from(getRouteModel().getEntry());
	}

	@Override
	protected ProcessorDefinition routeLogic(ProcessorDefinition process) {
		process.log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.receiver",
				"Receive Message from: " + getRouteModel().getEntry())
				.setHeader(MetricsConstants.HEADER_METRIC_NAME,
						constant("consumed." + getRouteModel().getId() + ".counter"))
				.to("metrics:counter:name.not.used");
		return null;
	}

}
