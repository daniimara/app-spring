package com.example.demo.test.integration.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@SpringBootApplication(scanBasePackages = {"com.example.demo"})
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}