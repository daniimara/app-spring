package com.example.demo.core.exceptions;

import java.io.Serializable;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class JSONErrorMessage implements Serializable {
	private static final long serialVersionUID = 8260318463316185914L;
	
	private Integer errorCode;
	private String errorMessage;
	
	
	public JSONErrorMessage(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	// -- Getters and Setters -- //	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
