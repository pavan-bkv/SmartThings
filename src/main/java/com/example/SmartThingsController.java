package com.example;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

@RestController
public class SmartThingsController {

	@Autowired
	private OAuth2RestOperations oAuthRestTemplate;
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void auth() {

		RestTemplate template = new RestTemplate();
	    ResponseEntity<String> forEntity = oAuthRestTemplate
	            .getForEntity("https://graph.api.smartthings.com/api/smartapps/endpoints",
	                    String.class);
//	    return forEntity.getBody();
	}
	
	@RequestMapping(value = "/listEndpoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String listEndpoints(HttpSession session) {

//	    ResponseEntity<String> forEntity = oAuthRestTemplate
//	            .getForEntity("https://graph.api.smartthings.com/api/smartapps/endpoints",
//	                    String.class);
//	    return forEntity.getBody();
		//String accessToken = (String) session.getAttribute("access_token");
		System.out.println("Session token: " + oAuthRestTemplate.getAccessToken());
		
//	    ResponseEntity<String> forEntity = oAuthRestTemplate
//	            .getForEntity("http://sercverUsingOAuth2/rest/resourceToConsume",
//	                    String.class);
//	    return forEntity.getBody();
//		RestTemplate template = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", "Bearer " + accessToken);
//		HttpEntity<String> entity = new HttpEntity<>(headers);
		return oAuthRestTemplate.getForObject("https://graph.api.smartthings.com/api/smartapps/endpoints",  String.class);
		//return template.exchange("https://graph.api.smartthings.com/api/smartapps/endpoints", HttpMethod.GET, entity, String.class).getBody();
	}
	
//	@RequestMapping(value = "/captureToken", method = RequestMethod.GET)
//	public Map<String, String[]> captureToken(WebRequest request, HttpSession session) {
//		System.out.println("Param Map : " + request.getParameterMap());
//		session.setAttribute("access_token", oAuthRestTemplate.getAccessToken());
//		
//		return request.getParameterMap();
//	}
	
	@RequestMapping(value = "/captureToken", method = RequestMethod.GET)
	public Map<String, String[]> captureToken(WebRequest request, HttpSession session) {
		System.out.println("Param Map : " + request.getParameterMap());
		session.setAttribute("access_token", oAuthRestTemplate.getAccessToken());
		
		return request.getParameterMap();
	}

	@RequestMapping(value = "/listSwitches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProducts(HttpSession session) {
		String accessToken = (String) session.getAttribute("access_token");
		System.out.println("Session token: " + accessToken);
		
//	    ResponseEntity<String> forEntity = oAuthRestTemplate
//	            .getForEntity("http://sercverUsingOAuth2/rest/resourceToConsume",
//	                    String.class);
//	    return forEntity.getBody();
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return template.exchange("http://sercverUsingOAuth2/rest/resourceToConsume", HttpMethod.GET, entity, String.class).getBody();
	}
}
