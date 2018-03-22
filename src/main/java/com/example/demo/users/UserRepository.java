package com.example.demo.users;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.demo.data.repository.BaseRepository;
import com.example.demo.security.DatabaseEncryptionClass;
import com.example.demo.users.UserDocument;
import com.mongodb.BasicDBObject;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@DatabaseEncryptionClass
@Component
public class UserRepository extends BaseRepository<UserDocument> {
	
	@Autowired
	public UserRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate);
	}
	
	public UserDocument saveUser(UserDocument user) {
		mongoTemplate.save(user);
		return this.findById(user.getId());
	}
	
	public UserDocument findById(String userID){
		Query query = new Query(where("_id").is(userID));		
		return super.findOne(query);
	}
	
}
