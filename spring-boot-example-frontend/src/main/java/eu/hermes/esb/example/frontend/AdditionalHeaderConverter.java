package eu.hermes.esb.example.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

import eu.hermes.esb.example.frontend.model.HeaderModel;

public class AdditionalHeaderConverter extends DozerConverter<Map, List> {

	public AdditionalHeaderConverter() {
		super(Map.class, List.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<HeaderModel> convertTo(Map source, List destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> convertFrom(List source, Map destination) {
		destination = new HashMap<String, String>();
		for (HeaderModel object : (List<HeaderModel>) source) {
			if (StringUtils.isNotEmpty(object.getKey()))
				destination.put(object.getKey(), object.getValue());
		}

		return destination;
	}

}
