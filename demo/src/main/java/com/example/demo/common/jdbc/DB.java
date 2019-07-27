package com.example.demo.common.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD,TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RUNTIME)
@Documented
public @interface DB {

	/**
	 * 名称
	 * @return
	 */
	String value() default "";
}
