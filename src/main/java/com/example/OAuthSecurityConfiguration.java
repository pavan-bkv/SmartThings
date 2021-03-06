package com.example;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${oauth.resource:https://graph.api.smartthings.com}")
	private String baseUrl;

	@Value("${oauth.authorize:https://graph.api.smartthings.com/oauth/authorize}")
	private String authorizeUrl;

	@Value("${oauth.token:https://graph.api.smartthings.com/oauth/token}")
	private String tokenUrl;
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Bean
	public OAuth2RestOperations oauth2RestTemplate() {
		
	    return new OAuth2RestTemplate(resource(), oauth2ClientContext);
	}
	
	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
	    AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
	    resource.setAccessTokenUri(tokenUrl);
	    resource.setUserAuthorizationUri(authorizeUrl);
	    resource.setClientId("b6b2699b-e623-4db7-a539-893da77068f5");
	    resource.setClientSecret("15a785a1-59c3-45d9-ad4c-68fa3d93ec58");
	    resource.setScope(Arrays.asList(new String [] {"app"} ));
	    resource.setAuthenticationScheme(AuthenticationScheme.form);
	    resource.setUseCurrentUri(false);
	    return resource;
	}
	
	@Override
    public void init(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
    }

}
