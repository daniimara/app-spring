package com.example.demo.users;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@JsonInclude(Include.NON_NULL)
public class UserTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userID;
	private String name;
	private Boolean isActive;
	
	// -- Getters and Setters -- //
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
