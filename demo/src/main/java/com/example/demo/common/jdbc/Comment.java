package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.Comment.Comments;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD,TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RUNTIME)
@Documented
@Repeatable(Comments.class)
public  @interface Comment {

	/**
	 * 字段中文名称
	 * @return
	 */
	String[] value() default {};
	
	/**
	 * 字段备注
	 * @return
	 */
	String  memo() default "";
	/**
	 * 外键
	 * 
	 * @return
	 */
	Class<?> []  foreign() default {};
	
	@Target({METHOD, FIELD,TYPE,ElementType.ANNOTATION_TYPE}) 
	@Retention(RUNTIME)
	@Documented
	public static @interface Comments{
		Comment[] value();
	}
}
