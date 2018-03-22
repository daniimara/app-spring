package com.example.demo.core.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.example.demo.users.UserResource;
import com.example.demo.core.exceptions.BusinessExceptionMapper;
import com.example.demo.core.exceptions.ExceptionMapper;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Configuration
@ApplicationPath("/project/api")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig(){
		register(MultiPartFeature.class);
		register(UserResource.class);
		register(ExceptionMapper.class);
		register(BusinessExceptionMapper.class);
	}
	
}
