package com.ca.biere.local.quebec.commons.ws.config;

import javax.validation.Validator;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan("com.ca.biere.local.quebec.commons.ws")
@PropertySource({ "classpath:commons-messages.properties" })
public class CommonsConfig {
	
	/** este bean é para resolver o problema: 
	 * java.lang.IllegalArgumentException: Invalid character found in the request target. 
	 * The valid characters are defined in RFC 7230 and RFC 3986 
	 * **/
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
	        @Override
	        public void customize(Connector connector) {
	            connector.setProperty("relaxedQueryChars", "|{}[]");
	        }
	    });
	    return factory;
	}
	
	@Bean
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}
}
