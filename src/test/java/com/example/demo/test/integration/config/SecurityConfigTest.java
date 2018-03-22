package com.example.demo.test.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Configuration
@Order(value=1)
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
	
	public SecurityConfigTest() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Disable CSRF
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
				.antMatchers("/api/v1/**").permitAll();
	}

}
