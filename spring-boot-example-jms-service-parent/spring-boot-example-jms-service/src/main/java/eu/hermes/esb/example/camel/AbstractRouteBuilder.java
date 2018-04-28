package eu.hermes.esb.example.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;

import eu.hermes.esb.example.model.RouteModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractRouteBuilder<T extends RouteModel> extends RouteBuilder {

	private final T routeModel;
	private final String routePrefix;

	@Override
	public void configure() throws Exception {
		RouteDefinition definition = withStart().routeId(getCamelRouteId()).tracing();

		ProcessorDefinition process;
		if (routeModel.isTransacted())
			process = definition.transacted();
		else
			process = definition;

		routeLogic(process);

	}

	public String getCamelRouteId() {
		return routePrefix + routeModel.getId();
	}

	protected abstract ProcessorDefinition routeLogic(ProcessorDefinition process);

	protected abstract RouteDefinition withStart();

}
