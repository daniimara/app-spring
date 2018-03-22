package com.example.demo.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Configuration
@EnableWebSecurity
@Profile(value = {"development", "production"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public SpringAuthenticationProvider springAuthenticationProvider() {
		return new SpringAuthenticationProvider();
	}
	
	@Bean
	public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
		return new JwtAuthenticationSuccessHandler();
	}
	
	@Bean
	public JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler() {
		return new JwtAuthenticationFailureHandler();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
		filter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler());
		filter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler());
		return filter;
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Disable CSRF
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers(HttpMethod.GET, "/images/**").permitAll()
			.antMatchers(HttpMethod.GET, "/project/images/**").permitAll()
			.antMatchers(HttpMethod.POST, "/project/api/v1/users/**").permitAll()
			.antMatchers(HttpMethod.GET, "/project/api/v1/users/**").permitAll()
			.antMatchers(HttpMethod.PUT, "/project/api/v1/users/**").permitAll()
			.antMatchers(HttpMethod.DELETE, "/project/api/v1/users/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.exceptionHandling()
				.accessDeniedHandler(new CustomAccessDeniedEntryPoint())
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		.and()
			.addFilterBefore(jwtAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
		;
		
		// disable page caching
		http.headers().cacheControl();
		
	}

}
