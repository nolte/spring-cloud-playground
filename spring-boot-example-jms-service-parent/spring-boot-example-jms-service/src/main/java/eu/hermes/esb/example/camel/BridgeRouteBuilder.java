package eu.hermes.esb.example.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.component.jms.JmsConstants;
import org.apache.camel.component.metrics.MetricsConstants;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;

import eu.hermes.esb.example.model.BridgeRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BridgeRouteBuilder extends AbstractRouteBuilder<BridgeRouteModel> {

	public static final String PREFIX = "bridge";

	public BridgeRouteBuilder(BridgeRouteModel routeModel) {
		super(routeModel, PREFIX);
	}

	@Override
	protected ProcessorDefinition routeLogic(ProcessorDefinition process) {
		return process
				.log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.bridge",
						"forward message from:" + getRouteModel().getEntry() + " to:" + getRouteModel().getExit())
				.setHeader(MetricsConstants.HEADER_METRIC_NAME,
						constant("bridged." + getRouteModel().getId() + ".counter"))
				.setHeader(JmsConstants.JMS_DESTINATION, constant(getRouteModel().getExit())).to("direct:produceJms")
				.end();
	}

	@Override
	protected RouteDefinition withStart() {
		return from(getRouteModel().getEntry());
	}

}
