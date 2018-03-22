package com.example.demo.data.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public abstract class BaseRepository<T extends DocumentInterface> {
	
	protected MongoTemplate mongoTemplate;
	
	public BaseRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Save the object
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity, Query query) {
		mongoTemplate.insert(entity);		
		return mongoTemplate.findOne(query, getGenericTypeClass()); 		
	}
	
	/**
	 * Save the object
	 * 
	 * @param entity
	 * @return
	 */
	public void save(T entity) {
		mongoTemplate.insert(entity);		
	}
	
	/**
	 * Find all
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return mongoTemplate.findAll(getGenericTypeClass());
	}
	
	/**
	 * Find by query
	 * 
	 * @return
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, getGenericTypeClass());
	}
	
	/**
	 * Find one
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, getGenericTypeClass());
	}
	
	/**
	 * Update the first record
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public WriteResult updateFirst(Query query, Update update) {
		return mongoTemplate.updateFirst(query, update, getGenericTypeClass());
	}
	
	/**
	 * Update Multiple records
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public WriteResult updateMulti(Query query, Update update) {
		return mongoTemplate.updateMulti(query, update, getGenericTypeClass());
	}
	
	/**
	 * Remove a object
	 * @param query
	 * @return
	 */
	public WriteResult removeObject(Query query) {
		return mongoTemplate.remove(query, getGenericTypeClass());
	}
	
	/**
	 * Remove a object
	 * @param query
	 * @return
	 */
	public WriteResult remove(T obj) {
		return mongoTemplate.remove(obj);
	}
	
	
	// -- Private Methods -- //
	@SuppressWarnings("unchecked")
    private Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    } 	

}
