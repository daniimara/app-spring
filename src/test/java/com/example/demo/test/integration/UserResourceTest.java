package com.example.demo.test.integration;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.example.demo.test.integration.config.AbstractSpringUnitTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.example.demo.users.UserTO;

/**
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class UserResourceTest extends AbstractSpringUnitTest{

	private static final String COLLECTION_NAME = "Users";

	private static final Integer USER_NOT_FOUND = 404;
	private static final Integer USER_FIELD_ERROR = 400;

	@Before
	public void beforeEach() throws Exception {
		//Load the Users into the test database
		importJSON(COLLECTION_NAME, "src/test/resources/datasets/userDocument.json");
	}
	
	// -- Positive Tests -- //	
	@Test
	public void testGetUserById() {

        Response response = RestAssured.given()
	        .contentType("application/json")
	        .pathParam("userID", "5ab3c1cf95277de3ca2ec9dc")
	        .when().get("/v1/users/{userID}")
	        .then().statusCode(200)
	        .and().contentType(ContentType.JSON)
	        .extract().response();

        UserTO userTO = response.as(UserTO.class);
        Assert.notNull(userTO, "The User object cannot be null.");
        Assert.notNull(userTO.getUserID(), "The User ID cannot be null.");
        Assert.isTrue(userTO.getUserID().equals("5ab3c1cf95277de3ca2ec9dc"), "The User ID is invalid.");
        Assert.notNull(userTO.getName(), "The Name cannot be null.");
        Assert.isTrue(userTO.getName().equals("Dan"), "The Name is invalid.");
        Assert.notNull(userTO.getIsActive(), "The isActive field cannot be null.");
        Assert.isTrue(userTO.getIsActive(), "The isActive field is invalid.");

	}
	
	@Test
	public void testDeleteUserById() {

        RestAssured.given()
	        .contentType("application/json")
	        .pathParam("userID", "5ab3c1cf95277de3ca2ec9dc")
	        .when().delete("/v1/users/{userID}")
	        .then().statusCode(200);
        
        //To make sure that the user was soft-deleted,
        //we'll retrieve it and check for isActive
        Response response = RestAssured.given()
    	        .contentType("application/json")
    	        .pathParam("userID", "5ab3c1cf95277de3ca2ec9dc")
    	        .when().get("/v1/users/{userID}")
    	        .then().statusCode(200)
    	        .and().contentType(ContentType.JSON)
    	        .extract().response();
        
        UserTO reponseTO = response.as(UserTO.class);
        Assert.notNull(reponseTO, "The User object cannot be null.");
        Assert.notNull(reponseTO.getIsActive(), "The isActive Field must be null.");
        Assert.isTrue(!reponseTO.getIsActive(), "The user is still Active in the database.");

	}
	
	// -- Negative Tests -- GET -- //
	@Test
	public void testGetUserByInvalidId() {

        Response response = RestAssured.given()
	        .contentType("application/json")
	        .pathParam("userID", RandomStringUtils.random(10))
	        .when().get("/v1/users/{userID}")
	        .then().statusCode(400)
	        .and().contentType(ContentType.JSON)
	        .extract().response();

       this.verifyErrorCode(response.asString(), USER_NOT_FOUND);
        
	}
	
	// -- Negative Tests -- DELETE -- //
	@Test
	public void testDeleteUserByInvalidId() {

        Response response = RestAssured.given()
	        .contentType("application/json")
	        .pathParam("userID", RandomStringUtils.random(10))
	        .when().delete("/v1/users/{userID}")
	        .then().statusCode(400)
	        .and().contentType(ContentType.JSON)
	        .extract().response();

       this.verifyErrorCode(response.asString(), USER_NOT_FOUND);
        
	}
	
	// -- Negative Tests -- ADD -- //
	@Test
	public void testAddUserInvalidName() {

		UserTO userTO = this.createUserTO();
		//updating the object to generate an error.
		userTO.setName(null);
		
        Response response = RestAssured.given()
	        .contentType("application/json")
	        .body(userTO)
	        .when().post("/v1/users/")
	        .then().statusCode(400)
	        .and().contentType(ContentType.JSON)
	        .extract().response();

       this.verifyErrorCode(response.asString(), USER_FIELD_ERROR);
        
	}
	
	// -- Private Methods -- //
	private UserTO createUserTO() {
		UserTO userTO = new UserTO();
		userTO.setUserID("5ab3c1cf95277de3ca2ec9dc");
		userTO.setName("Dan");
		userTO.setIsActive(true);
		return userTO;
	}

}
