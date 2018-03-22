package com.example.demo.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.data.repository.DocumentInterface;

/**
 * 
 * @author Daniela Mara - maravaz@br.ibm.com
 *
 */
@Component
@Aspect
public class DatabaseEncryptionAspect {
	
	private static final Logger LOGGER = Logger.getLogger( DatabaseEncryptionAspect.class.getName() );
	private static final boolean ENCRYPT = true;
	private static final boolean DECRYPT = false;
	
	@Value("${security.database.encryption}")
	private Boolean isEncriptionOn;
	
	private EncryptDecryptConverter converter = new EncryptDecryptConverter();
	
	@Pointcut("@within(com.example.demo.security.DatabaseEncryptionClass) || @annotation(com.example.demo.security.DatabaseEncryptionClass)")
    private void annotatedClasses() {}

	@Pointcut("execution(* find*(..))")
    private void allFindMethods() {}

	@Pointcut("execution(* list*(..))")
    private void allListMethods() {}

	@Pointcut("execution(* save*(..))")
	private void allSaveMethods() {}
	
	@Pointcut("execution(* update*(..))")
	private void allUpdateMethods() {}
	
	
	@Around("annotatedClasses() && (allFindMethods() || allListMethods())")
	public Object allSearchesInAnnotatedClasses(ProceedingJoinPoint joinPoint) throws Throwable {
		
		if (!isEncriptionOn) {
			return joinPoint.proceed();
		}

		Signature signature = joinPoint.getSignature();
		String triggerClassName = signature.getDeclaringType().getSimpleName();
		String triggerMethod = signature.getName();
		LOGGER.log(Level.INFO, "DatabaseEncryptionAspect.allSearchesInAnnotatedClasses, class: " + triggerClassName + ", method: " + triggerMethod);
		
		Object[] args = joinPoint.getArgs();
		
		if (args == null || args.length == 0) {
			return joinPoint.proceed();
		}
		
		Object[] newArgs = new Object[args.length];
		
		for (int i = 0; i < args.length; i++) {
			newArgs[i] = callSetters(args[i], ENCRYPT);
		}
				
		Object obj = joinPoint.proceed(newArgs);

		callSetters(obj, DECRYPT);

		return obj;
	}
	
	@Around("annotatedClasses() && (allSaveMethods() || allUpdateMethods())")
	public Object allInsertsInAnnotatedClasses(ProceedingJoinPoint joinPoint) throws Throwable {
		
		if (!isEncriptionOn) {
			return joinPoint.proceed();
		}
		
		Signature signature = joinPoint.getSignature();
		String triggerClassName = signature.getDeclaringType().getSimpleName();
		String triggerMethod = signature.getName();
		LOGGER.log(Level.INFO, "DatabaseEncryptionAspect.allInsertsInAnnotatedClasses, class: " + triggerClassName + ", method: " + triggerMethod);
		
		Object[] args = joinPoint.getArgs();
		
		if (args == null || args.length == 0) {
			return joinPoint.proceed();
		}
		
		Object[] newArgs = new Object[args.length];
		
		for (int i = 0; i < args.length; i++) {
			newArgs[i] = callSetters(args[i], ENCRYPT);
		}
				
		return joinPoint.proceed(newArgs);
	}
	
	private Object callSetters(Object obj, boolean encrypt) throws Exception {
		
		if (obj == null) {
			return null;
		}
		
		if (obj instanceof String) {
			
			String value = (String) obj;
			String newValue = encrypt ? converter.convertToDatabaseColumn(value) : converter.convertToEntityAttribute(value);
			if (StringUtils.isNotBlank(newValue)) {
				return newValue;
			}
			return value;
			
		} else if (obj instanceof Collection<?>) {
			
			Collection<?> collection = (Collection<?>) obj;
			Collection<Object> newCollection = new ArrayList<Object>();
			for (Object item : collection) {
				newCollection.add(callSetters(item, encrypt));
			}
			return newCollection;
			
		} else if (obj instanceof DocumentInterface) {
			
			Class<?> curClass = obj.getClass();
			
			for (Field field : curClass.getDeclaredFields()) {
				
				field.setAccessible(true);
				
				final String fieldName = field.getName();
				if (fieldName.equals("id") || fieldName.endsWith("Id") || fieldName.endsWith("ID")) {
					continue;
				}
					
				Class<?> type = field.getType();				
				if (type.equals(String.class)) {
					
					String value = (String)field.get(obj);
					if (value != null) {
						String newValue = encrypt ? converter.convertToDatabaseColumn(value) : converter.convertToEntityAttribute(value);
						if (StringUtils.isNotBlank(newValue)) {
							field.set(obj, newValue);
						}
					}
					
				} else if (type.isArray()) {
					
					Object[] array = (Object[])field.get(obj);
					if (array != null) {
						for (int i = 0; i < array.length; i++) {
							array[i] = callSetters(array[i], encrypt);
						}
						field.set(obj, array);
					}
					
				} else if (type.isInstance(DocumentInterface.class)) {
					
					Object document = (Object)field.get(obj);
					field.set(obj, callSetters(document, encrypt));
					
				}
			}
		}
		
		return obj;
	}
	
}
