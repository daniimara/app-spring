package com.example.demo.users;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.data.repository.DocumentInterface;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Document(collection = "Users")
public class UserDocument implements Serializable, DocumentInterface {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String Id;
	private String name;
	private Boolean isActive;
	
	// -- Getters and Setters -- //
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
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
