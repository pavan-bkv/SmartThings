package com.example;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SmartThingsController {

	@Autowired
	private OAuth2RestOperations oAuthRestTemplate;
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void auth() {

	    oAuthRestTemplate
	           .getForEntity("https://graph.api.smartthings.com/api/smartapps/endpoints", String.class);
	}
	
	@RequestMapping(value = "/listEndpoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String listEndpoints(HttpSession session) {

		return oAuthRestTemplate.getForObject("https://graph.api.smartthings.com/api/smartapps/endpoints",  String.class);
	}
	
	@RequestMapping(value = "/captureToken", method = RequestMethod.GET)
	public void captureToken(WebRequest request, HttpSession session, HttpServletResponse response ) throws IOException {
		
		response.sendRedirect("/listSwitches");
	}

	@RequestMapping(value = "/listSwitches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProducts(HttpSession session) throws JsonProcessingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String endpoints = oAuthRestTemplate.getForObject("https://graph.api.smartthings.com/api/smartapps/endpoints",  String.class);
		JsonNode rootNode = objectMapper.readTree(endpoints);
		String url = rootNode.get(0).path("uri").asText();
		
		return oAuthRestTemplate.getForObject(url + "/switches",  String.class);
	}
	
}
