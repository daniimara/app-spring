package com.example.demo.core.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 4885774371380510904L;
	
	private String role;

	@Override
	public String getAuthority() {
		return this.role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
