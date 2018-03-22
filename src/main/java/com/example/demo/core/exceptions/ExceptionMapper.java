package com.example.demo.core.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.example.demo.shared.util.PropertiesUtil;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
	
	private static final Logger LOGGER = Logger.getLogger( ExceptionMapper.class.getName() );

	@Override
	public Response toResponse(Exception exception) {
		//Log first
		LOGGER.log( Level.SEVERE, exception.toString(), exception );
		//Check if there is a constraint violation exception
		if (exception.getCause() != null && exception.getCause().getCause() != null && exception.getCause().getCause().getMessage().contains("ConstraintViolationImpl")) {
			System.out.println(exception.getCause().getCause().getMessage());	
			if (exception.getCause().getCause().getMessage().contains("ConstraintViolationImpl")) {
			
			}
		}				
		//Get the Error Message
	    PropertiesUtil propertiesUtil = new PropertiesUtil("internal_server_error");
		//Create the Error Message
		JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());		
		//Return the JSON file
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
