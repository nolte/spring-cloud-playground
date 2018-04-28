package eu.hermes.esb.example.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsConstants;
import org.apache.camel.component.metrics.MetricsConstants;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.fluttercode.datafactory.impl.DataFactory;

import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ProduceRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CronProduceRouteBuilder extends AbstractRouteBuilder<ProduceRouteModel> {

	public static final String PREFIX = "producing";

	public CronProduceRouteBuilder(ProduceRouteModel routeModel) {
		super(routeModel, PREFIX);
	}

	@Override
	protected ProcessorDefinition routeLogic(ProcessorDefinition process) {
		process.setBody(constant((new DataFactory().getRandomText(getRouteModel().getProducedSize())))).setHeader(
				MetricsConstants.HEADER_METRIC_NAME, constant("produce." + getRouteModel().getId() + ".counter"));

		if (getRouteModel().getAdditionalHeaders() != null && !getRouteModel().getAdditionalHeaders().isEmpty()) {
			process.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					// add additional headers to the message from the configuration
					for (String key : getRouteModel().getAdditionalHeaders().keySet()) {
						exchange.getIn().setHeader(key, getRouteModel().getAdditionalHeaders().get(key));
					}
				}
			});
		}

		return process
				.setHeader(JmsConstants.JMS_DESTINATION, constant(getRouteModel().getExit())).log(LoggingLevel.INFO,
						"eu.hermes.esb.example.jms.produce.cron", "Produce Message to: ${headers.CamelJmsDestination}")
				.to("direct:produceJms");
	}

	@Override
	protected RouteDefinition withStart() {
		return from("quartz2://timer" + getRouteModel().getId() + "?cron=" + getRouteModel().getCron());
	}

}
