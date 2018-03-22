package com.example.demo.shared.exceptions;

import com.example.demo.shared.enums.Errors;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 8986359222193094168L;
	
	private String key;
	private String[] fields;
	
	public BusinessException(Errors errors) {
		this.key = errors.getKey();
	}
	
	public BusinessException(Errors errors, String... fields) {
		this.key = errors.getKey();
		this.fields = fields;
	}

	public String getKey() {
		return this.key;
	}
	
	public String[] getFields() {
		return this.fields;
	}
	
}
