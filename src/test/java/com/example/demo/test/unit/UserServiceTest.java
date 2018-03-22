package com.example.demo.test.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.example.demo.shared.exceptions.BusinessException;
import com.example.demo.shared.exceptions.UserFieldErrorException;
import com.example.demo.shared.exceptions.UserNotFoundException;
import com.example.demo.users.UserDocument;
import com.example.demo.users.UserRepository;
import com.example.demo.users.UserService;
import com.example.demo.users.UserTO;


/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class UserServiceTest {
	
	private UserService userService;
	
	//Mock
	private UserRepository userRepositoryMock = mock(UserRepository.class);
	
	@Before
	public void beforeEach() throws Exception {
		//Reset the mock repository
		reset(userRepositoryMock);
		
		//Instantiate the tested service
		userService = new UserService(userRepositoryMock);
	}
	
	
    // -- Positive Tests --//
	/**
	 * Expected: user's id can not be null
	 */
	@Test
	public void testAddUser() throws BusinessException {
		//Create mock object for insertion
		UserTO user = this.createUserTO();
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.findById(user.getUserID()))
				.thenReturn(mockUserObject);
		when(userRepositoryMock
				.saveUser((UserDocument)notNull()))
				.thenReturn(mockUserObject);
		
		//Call the service layer
		user = userService.create(user);
		//Assert
		assertTrue(true);
		//Check that the method was actually called
		verify(userRepositoryMock, times(1)).saveUser((UserDocument)notNull());
	}

	/**
	 * Expected: the User cannot be null
	 */
	@Test
	public void testGetUser() throws BusinessException {
		
		UserTO mockUserTO = this.createUserTO();
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.findById(mockUserTO.getUserID()))
				.thenReturn(mockUserObject);
		
		UserTO user = userService.get(mockUserTO.getUserID());
		Assert.notNull(user, "The User cannot be null.");
		//Assert
		assertTrue(true);
		//Check that the method was actually called
		verify(userRepositoryMock).findById(mockUserTO.getUserID());
	}

	// -- Negative Tests -- ADD -- //
	@Test
	public void testAddUserWithNullObject() {
		//Create mock object for insertion
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.saveUser((UserDocument)notNull()))
				.thenReturn(mockUserObject);
		
		//Call the service layer
		try {
			userService.create(null);
			fail("The method supposed to throw an exception before this point");
		} catch (BusinessException exception) {
			//Check the exception
			if (exception instanceof UserNotFoundException) {
				assertTrue(true);
			} else {
				fail("Expected a UserNotFoundException exception, but actual: " + exception.getMessage());
			}			
		} finally {
			//Check that we didn't actually call the method, so the validation is working fine.
			verify(userRepositoryMock, never()).saveUser((UserDocument)notNull());
		}
		
	}
	
	@Test
	public void testAddUserWithEmptyName() {
		//Create mock object for insertion
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.saveUser((UserDocument)notNull()))
				.thenReturn(mockUserObject);
		UserTO userTO = this.createUserTO();
		//Change the mock object to return an error
		userTO.setName(null);
		
		//Call the service layer
		try {
			userService.create(userTO);
			fail("The method supposed to throw an exception before this point");
		} catch (BusinessException exception) {
			//Check the exception
			if (exception instanceof UserFieldErrorException) {
				assertTrue(true);
			} else {
				fail("Expected a UserFieldErrorException exception, but actual: " + exception.getMessage());
			}			
		} finally {
			//Check that we didn't actually call the method, so the validation is working fine.
			verify(userRepositoryMock, never()).saveUser((UserDocument)notNull());
		}
		
	}
	
	// -- Negative Tests -- DELETE -- //
	@Test
	public void testDeleteUserWithNullObject() {
		//Create mock object for insertion
		UserTO mockUserTO = this.createUserTO();
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.findById(mockUserTO.getUserID()))
				.thenReturn(mockUserObject);
		
		//Call the service layer
		try {
			userService.delete(null);
			fail("The method supposed to throw an exception before this point");
		} catch (BusinessException exception) {
			//Check the exception
			if (exception instanceof UserFieldErrorException) {
				assertTrue(true);
			} else {
				fail("Expected a UserFieldErrorException exception, but actual: " + exception.getMessage());
			}			
		} finally {
			//Check that we didn't actually call the method, so the validation is working fine.
			verify(userRepositoryMock, never()).remove(mockUserObject);
		}
		
	}
	
	@Test
	public void testDeleteUserWithInvalidID() {
		//Create mock object for insertion
		UserTO mockUserTO = this.createUserTO();
		UserDocument mockUserObject = this.createUserObject();
		when(userRepositoryMock
				.findById(mockUserTO.getUserID()))
				.thenReturn(mockUserObject);
		
		//Change the mock object to return an error
		mockUserTO.setUserID(RandomStringUtils.random(10));
		
		//Call the service layer
		try {
			userService.delete(mockUserTO.getUserID());
			fail("The method supposed to throw an exception before this point");
		} catch (BusinessException exception) {
			//Check the exception
			if (exception instanceof UserNotFoundException) {
				assertTrue(true);
			} else {
				fail("Expected a UserNotFoundException exception, but actual: " + exception.getMessage());
			}			
		} finally {
			//Check that the method was called exactly once.
			verify(userRepositoryMock, times(1)).findById(mockUserTO.getUserID());
			verify(userRepositoryMock, never()).remove(mockUserObject);
		}
		
	}

	// -- Private Methods -- //
	private UserTO createUserTO() {
		UserTO userTO = new UserTO();
		userTO.setUserID("1");
		userTO.setName("Dan");
		userTO.setIsActive(true);
		return userTO;
	}
	
	private UserDocument createUserObject() {
		UserDocument user = new UserDocument();
		user.setId("1");
		user.setName("Dan");
		user.setIsActive(true);
		return user;
	}
	
}
