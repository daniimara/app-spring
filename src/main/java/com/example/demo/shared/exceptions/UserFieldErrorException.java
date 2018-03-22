package com.example.demo.shared.exceptions;

import com.example.demo.shared.enums.Errors;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class UserFieldErrorException extends BusinessException {
	
	private static final long serialVersionUID = 1L;
	
	public UserFieldErrorException(String... fields) {
		super(Errors.USER_FIELD_ERROR, fields);
	}

}
