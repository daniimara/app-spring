package com.example.demo.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.demo.core.exceptions.JSONErrorMessage;
import com.example.demo.shared.util.PropertiesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(401);
		if (authException.getMessage().equals("Bad credentials")) {
			PropertiesUtil propertiesUtil = new PropertiesUtil("UsersObject_invalid_credentials");
			JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(errorMessage));
		} else {
			PropertiesUtil propertiesUtil = new PropertiesUtil("not_authorized");
			JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(errorMessage));			
		}		
	}

}

