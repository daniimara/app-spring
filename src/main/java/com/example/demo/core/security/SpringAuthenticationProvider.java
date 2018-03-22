package com.example.demo.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

//import com.example.demo.authentication.AuthenticationService;
import com.example.demo.shared.enums.Roles;
import com.example.demo.users.UserTO;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class SpringAuthenticationProvider implements AuthenticationProvider {
	
//	@Autowired
//	private AuthenticationService loginService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
//		String token = ((JwtAuthenticationToken) authentication).getToken();
//		
//		UserTO userTO = null;
//		try {
//			userTO = loginService.validateToken(token);
//		} catch (Exception e) {
//			throw new CredentialsExpiredException("Bad credentials");
//		}
//		
//		if (userTO == null) {
//			throw new CredentialsExpiredException("Bad credentials");
//		}
//		
//		//Verify if the user is valid and it has a role
//		if (userTO.getUserID() != null && userTO.getRole() != null) {
//			Role role = new Role();            
//			role.setRole(Roles.setRole(userTO.getRole()).name());
//			List<Role> roles = new ArrayList<>();
//			roles.add(role);            
//			Collection<? extends GrantedAuthority> authorities = roles;
//			return new UsernamePasswordAuthenticationToken(userTO, token, authorities);        	
//		} else {
//			throw new CredentialsExpiredException("Bad credentials");
//		}
		
		throw new CredentialsExpiredException("Bad credentials");
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}

