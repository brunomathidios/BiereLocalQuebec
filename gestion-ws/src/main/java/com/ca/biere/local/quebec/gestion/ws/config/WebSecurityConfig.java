package com.ca.biere.local.quebec.gestion.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.headers().frameOptions().disable()
				.and()
				.csrf().disable()
				.cors()
				.and()
				.authorizeRequests(auth ->
					auth.antMatchers("/gestion-ws/**").permitAll()
				)
			//.and().sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
		;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers( HttpMethod.OPTIONS , "/**" );
	}
}
