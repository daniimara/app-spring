package com.example.demo.test.integration.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import com.jayway.restassured.RestAssured;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, MongoConfigTest.class, SecurityConfigTest.class})
@WebAppConfiguration
@IntegrationTest("server.port=8080")
@ActiveProfiles(value = "test")
public abstract class AbstractSpringUnitTest {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Import json to mongo database;
	 * @param collection collection name
	 * @param file json file will be imported
	 */
    protected void importJSON(String collection, String file) {
        try {
            for (Object line : FileUtils.readLines(new File(file), "utf8")) {
                mongoTemplate.save(line, collection);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not import file: " + file, e);
        }
    }
    
	/**
	 * Removes json collection from the mongo database;
	 * 
	 * @param collection collection name
	 */
    protected void dropCollection(String collection) {
    	
    	if (mongoTemplate.collectionExists(collection)) {
    		mongoTemplate.dropCollection(collection);
    	}
    	
    }
    
    /**
     * Checks if the errorCode returned by the Resource class is the one expected.
     * 
     * @param jsonResponse
     * @param expectedCode
     */
	protected void verifyErrorCode(String jsonResponse, Integer expectedCode) {
		try {
			JSONObject json = new JSONObject(jsonResponse);
			Integer errorCode = json.getInt("errorCode");
	        Assert.isTrue(errorCode.equals(expectedCode), "Invalid Error code returned. Expected [" + expectedCode + "], returned [" + errorCode +"]" );
		} catch (JSONException je) {
			//Rethrow as unchecked exception
			throw new RuntimeException(je);
		}
	}
    
    @BeforeClass
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.basePath = "/project/api/";
        RestAssured.baseURI = "http://localhost";
    }
}
