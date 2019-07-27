package com.example.demo.common.jdbc;

import org.springframework.core.annotation.AnnotationUtils;

public abstract class NameUtils {
	
	public static String withCatalogName(Class<?> entityClass) {
		DB db = AnnotationUtils.findAnnotation(entityClass, DB.class);
		if(db==null){
			return "";
		}
		return db.value();
	}

	public static String withTableName(Class<?> entityClass) {
		DB db = AnnotationUtils.findAnnotation(entityClass, DB.class);
		if(db==null){
			return entityClass.getSimpleName();
		}
		StringBuffer buf=new StringBuffer(db.value());
		buf.append(".");
		buf.append(entityClass.getSimpleName());
		return buf.toString();
	}
}
