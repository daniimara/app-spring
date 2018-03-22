package com.example.demo.shared.enums;

public enum Roles {
	
	ROLE_USER(0);
	
	private Integer role;
	
	Roles(Integer pRole) {
		this.role = pRole;
	}
	
	public Integer getRole() {
		return this.role;
	}
	
	public static Roles setRole(Integer role) {
		
		for (Roles roleEnum : Roles.values()) {
			if (roleEnum.getRole().equals(role)) {
				return roleEnum;
			}
		}
		
		return null;
	}
	
}
