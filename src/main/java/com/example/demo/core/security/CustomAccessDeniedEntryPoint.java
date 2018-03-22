package com.example.demo.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.core.exceptions.JSONErrorMessage;
import com.example.demo.shared.util.PropertiesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAccessDeniedEntryPoint implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(403);
		PropertiesUtil propertiesUtil = new PropertiesUtil("access_denied");
		JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(errorMessage));
		
	}

}

