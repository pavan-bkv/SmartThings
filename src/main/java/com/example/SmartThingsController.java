package com.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class SmartThingsController {

	@Autowired
	private OAuth2RestOperations oAuthRestTemplate;
	
	@RequestMapping(value = "/listEndpoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String listEndpoints() {

	    ResponseEntity<String> forEntity = oAuthRestTemplate
	            .getForEntity("https://graph.api.smartthings.com/api/smartapps/endpoints",
	                    String.class);
	    return forEntity.getBody();
	}
	
	@RequestMapping(value = "/captureToken", method = RequestMethod.GET)
	public Map<String, String[]> captureToken(WebRequest request) {
		System.out.println("Param Map : " + request.getParameterMap());
		return request.getParameterMap();
	}

	@RequestMapping(value = "/listSwitches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProducts() {

	    ResponseEntity<String> forEntity = oAuthRestTemplate
	            .getForEntity("http://sercverUsingOAuth2/rest/resourceToConsume",
	                    String.class);
	    return forEntity.getBody();
	}
}
