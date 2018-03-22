package com.example.demo.core.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.demo")
@PropertySource("classpath:application.properties")
public class MongoConfig extends AbstractMongoConfiguration {
	private final static String DB_HOST = "db.host";
	private final static String DB_PORT = "db.port";
	
	@Resource
	private Environment env;
 
    @Override
    protected String getDatabaseName() {
        return "springdatabase";
    }
 
    @Override
    public Mongo mongo() throws Exception {
        int port = Integer.parseInt(env.getRequiredProperty(DB_PORT));
		return new MongoClient(env.getRequiredProperty(DB_HOST), port);
    }
    
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }    

}
