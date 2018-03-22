package com.example.demo.core.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.demo.shared.util.PropertiesUtil;


/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	
	private static final Logger LOGGER = Logger.getLogger( ConstraintViolationExceptionMapper.class.getName() );

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		//Log first
		LOGGER.log( Level.INFO, exception.toString(), exception );
		
		List<Map<String, ?>> data = new ArrayList<>();
	    Map<String, String> errorMap;
	    for (final ConstraintViolation<?> error : exception.getConstraintViolations()) {
	      errorMap = new HashMap<>();
	      errorMap.put("attribute", error.getPropertyPath().toString());
	      errorMap.put("message", error.getMessage());
	      errorMap.put("class", error.getRootBeanClass().getSimpleName());
	      data.add(errorMap);
	    }
	    //Get the Messages from Properties
	    String key = data.get(0).get("class") + "_" + data.get(0).get("attribute") + "_" + data.get(0).get("message");
		//Get the Error Message
	    PropertiesUtil propertiesUtil = new PropertiesUtil(key);
		//Create the Error Message
		JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());		
		//Return the JSON file
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
