package eu.hermes.esb.example.web.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class RouteNotExistException extends RuntimeException {
	String routeId;
}
