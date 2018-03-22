package com.example.demo.users;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.shared.exceptions.BusinessException;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Component
@Path("/v1/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Retrieve all existing users.
	 * 
	 * @return an array of {@link UserTO} Objects
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() throws BusinessException {		
		UserTO[] users = userService.list();
		//Return success
		return Response.status(200).entity(users).build();
	}
	
	/**
	 * Retrieve an existing user by ID.
	 * 
	 * @param userID The ID of the user to be retrieved.
	 * @return a {@link userTO} Object
	 */
	@GET
	@Path("/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("userID") String userID) throws BusinessException {		
		UserTO userTO = userService.get(userID);
		//Return success
		return Response.status(200).entity(userTO).build();
	}
	
	/**
	 * Add a new user into the system
	 * 
	 * @param userTO
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(UserTO userTO) throws BusinessException {		
		userTO = userService.create(userTO);
		//Return success
		return Response.status(201).entity(userTO).build();
	}
	
	/**
	 * Update an existing user
	 * 
	 * @param userTO
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(UserTO userTO) throws BusinessException {		
		userTO = userService.edit(userTO);
		//Return success
		return Response.status(200).entity(userTO).build();
	}
	
	/**
	 * Removes an existing user by ID.
	 * 
	 * @param userID The ID of the user to be removed.
	 * @return a {@link userTO} Object
	 */
	@DELETE
	@Path("/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("userID") String userID) throws BusinessException {		
		userService.delete(userID);
		//Return success
		return Response.status(200).build();
	}
	

}
