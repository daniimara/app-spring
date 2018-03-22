package com.example.demo.users;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.shared.exceptions.BusinessException;
import com.example.demo.shared.exceptions.UserNotFoundException;
import com.example.demo.users.UserDocument;
import com.example.demo.users.UserTO;
import com.example.demo.shared.exceptions.UserFieldErrorException;


/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Service
public class UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * Retrieves a list of {@link UserDocument} from the database.
	 * 
	 * @return a list of {@link UserTO} objects;
	 * {@code null} otherwise
	 */
	public UserTO[] list() throws BusinessException {
		
		//List from the database
		List<UserDocument> documents = this.userRepository.findAll();
		
		if (documents == null || documents.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		UserTO[] objectArray = new UserTO[documents.size()];
		int i = 0;
		for (UserDocument document : documents) {
			objectArray[i++] = this.populateUserTO(document);;
		}		
		
		return objectArray;
	}
	
	/**
	 * Add a new {@link UserDocument} into the database
	 * @param userTO
	 */
	public UserTO create(UserTO userTO) throws BusinessException {
		
		//Validate the parameter object
		this.validateSave(userTO);
		
		UserDocument user = this.populateUsersObject(userTO);
		user.setIsActive(true);
		
		//Insert or update the user object
		user = userRepository.saveUser(user);
		
		//Update the UserTO object
		userTO = this.populateUserTO(user);
		
		return userTO;
	}
	
	/**
	 * Update an existing {@link UserDocument} in the database
	 * @param userTO
	 */
	public UserTO edit(UserTO userTO) throws BusinessException {

		if (userTO == null) {
			throw new UserNotFoundException();
		}
		
		//Validate the parameter object
		this.validateSave(userTO);
		
		//Copy the values from TO to Model
		UserDocument user = this.populateUsersObject(userTO);
		user.setIsActive(true);
		
		//Save into the database
		user = userRepository.saveUser(user);
		
		//Update the UserTO object
		userTO = this.populateUserTO(user);

		return userTO;
	}
	
	/**
	 * Delete an existing {@link UserDocument} from the database
	 * 
	 * @param userID
	 */
	public void delete(String userID) throws BusinessException {
		
		if (userID == null || userID.trim().length() == 0) {
			throw new UserFieldErrorException("userID");
		}
		
		//Try to retrieve the User from the database. 
		//If not found, this method call will throw an exception.
		UserDocument user = userRepository.findById(userID);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		user.setIsActive(false);
		
		//Save into the database
		userRepository.saveUser(user);
		
	}
	
	/**
	 * Retrieves a {@link UserDocument} from the database.
	 * 
	 * @param userID
	 * @return a {@link UserTO} object if one exists with the corresponding ID;
	 * {@code null} otherwise
	 */
	public UserTO get(String userID) throws BusinessException {
		
		if (userID == null || userID.trim().length() == 0) {
			throw new UserFieldErrorException("userID");
		}
		
		UserDocument user = userRepository.findById(userID);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		UserTO userTO = this.populateUserTO(user);
		
		return userTO;
	}
	
	/**
	 * Validates the values sent in the {@link UserTO} object.
	 * 
	 * @param userTO
	 * @throws BusinessException
	 */
	private void validateSave(UserTO userTO) throws BusinessException {
		
		if (userTO == null) {
			throw new UserNotFoundException();
		}
		
		if (userTO.getName() == null || userTO.getName().trim().length() == 0) {
			throw new UserFieldErrorException("firstName");
		}
		
	}
	
	/**
	 * Populates a {@link UserDocument} based on a {@link UserTO} param.
	 * 
	 * @param userTO
	 * @return UsersObject
	 */
	private UserDocument populateUsersObject(UserTO userTO) {
		
		UserDocument user = new UserDocument();
		user.setId(userTO.getUserID());
		BeanUtils.copyProperties(userTO, user);
		
		return user;
	}
	
	/**
	 * Populates a {@link UserTO} based on a {@link UserDocument} param.
	 * 
	 * @param entity
	 * @return
	 */
	public UserTO populateUserTO(UserDocument entity) {
		
		UserTO userTO = new UserTO();
		
		userTO.setUserID(entity.getId());
		BeanUtils.copyProperties(entity, userTO);
		
		return userTO;
	}
	
}
