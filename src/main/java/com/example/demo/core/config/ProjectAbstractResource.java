package com.example.demo.core.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProjectAbstractResource {
	
	@Autowired
    private HttpServletRequest request;

}
