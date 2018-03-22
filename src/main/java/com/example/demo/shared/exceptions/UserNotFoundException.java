package com.example.demo.shared.exceptions;

import com.example.demo.shared.enums.Errors;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class UserNotFoundException extends BusinessException {
	
	private static final long serialVersionUID = -5762829229520088623L;
	
	public UserNotFoundException() {
		super(Errors.USER_NOT_FOUND);
	}

}
