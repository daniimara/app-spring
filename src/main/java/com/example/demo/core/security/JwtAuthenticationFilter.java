package com.example.demo.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
    public JwtAuthenticationFilter() {
        super("/**");
    }
    
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	String requestPath = request.getRequestURL().toString();
    	String requestMethod = request.getMethod().toUpperCase();
    	
    	Boolean requiresAuthentication = true;
    	
    	if (requestMethod.equals("OPTIONS")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/configs")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/images")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/users") && 
    			(requestMethod.equalsIgnoreCase("GET") 
    					|| requestMethod.equalsIgnoreCase("POST") 
    					|| requestMethod.equalsIgnoreCase("PUT")
    					|| requestMethod.equalsIgnoreCase("DELETE"))) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/media") && requestMethod.equalsIgnoreCase("GET")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/authenticate/login")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/authenticate/forgot") && requestMethod.equalsIgnoreCase("GET")) {
    		requiresAuthentication = false;
    	} else if (requestPath.contains("/authenticate/reset") && requestMethod.equalsIgnoreCase("POST")) {
    		requiresAuthentication = false;
    	} else if (!"POST GET PUT DELETE".contains(requestMethod)) {
    		requiresAuthentication = false;
    	}
    	
        return requiresAuthentication;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthenticationCredentialsNotFoundException("No Bearer header found in request.");
        }

        String authToken = header.substring(7);

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

        return getAuthenticationManager().authenticate(authRequest);
        
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
    
}
