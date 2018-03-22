package com.example.demo.shared.enums;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public enum Errors {
	
	ACCESS_DENIED("access_denied"),
	
	USER_NOT_FOUND("UserDocument_notfound"),
	USER_FIELD_ERROR("UserDocument_field_error")

	;
	
	private String key;
	
	private Errors(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
