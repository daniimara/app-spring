package com.example.demo.shared.util;

import java.util.Properties;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class PropertiesUtil {
	
	private Integer key;
	private String value;
	
	private static final Properties ERROR_MESSAGES = new Properties();
	
	static {
		try {
			ERROR_MESSAGES.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("errorCodes.properties"));
		} catch (Exception e) {
			//Can't let this exception go or it'll stop the server from starting.
		}
	}
	
	public PropertiesUtil(String key) {
		//Get the Error Message
		String message = ERROR_MESSAGES.getProperty(key);
		String[] keyValue = message.split(";");
		this.key = Integer.valueOf(keyValue[0]);
		this.value = keyValue[1];
	}
	
	public PropertiesUtil(String key, String... fields) {
		//Get the Error Message
		String message = ERROR_MESSAGES.getProperty(key);
		String[] keyValue = message.split(";");
		this.key = Integer.valueOf(keyValue[0]);
		this.value = keyValue[1];
		
		if (fields != null) {
			for (int i = 1; i <= fields.length; i++) {
				String index = "{" + i + "}";
				value = value.replace(index, fields[i - 1]);
			}
		}
	}
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	

}
