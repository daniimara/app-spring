package com.example.demo.core.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.demo.shared.exceptions.BusinessException;
import com.example.demo.shared.util.PropertiesUtil;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException businessException) {
		//Get the Error Message
	    PropertiesUtil propertiesUtil = new PropertiesUtil(businessException.getKey(), businessException.getFields());
		//Create the Error Message
		JSONErrorMessage errorMessage = new JSONErrorMessage(propertiesUtil.getKey(), propertiesUtil.getValue());		
		//Return the JSON file
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();		
	}

}
