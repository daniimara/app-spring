package com.example.demo.security;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
public class EncryptDecryptConverter {
	
	public EncryptDecryptConverter() {
		AES.setKey("springproject2018");
	}
	
	public String convertToDatabaseColumn(String attribute) {
		String result = null;
		if (attribute != null && !attribute.isEmpty()) {
			result = AES.encrypt(attribute);
		}
		return result;
	}

	public String convertToEntityAttribute(String attribute) {
		String result = null;
		if (attribute != null && !attribute.isEmpty()) {
			result = AES.decrypt(attribute);
		}
		return result;
	}

}
