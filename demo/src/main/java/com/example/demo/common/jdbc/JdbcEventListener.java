package com.example.demo.common.jdbc;

/**
 * 
 * 
 * 描述:jdbc操作数据库监听器
 * 
 * @author huangpengpeng
 * @version 1.0
 * @since 2013年12月23日 下午1:28:55
 */
public interface JdbcEventListener {
	
	/**
	 * 
			* 描述:增加数据 
			* @param entity
			* @param id
			* @author huangpengpeng2013年12月23日 下午1:40:04
	 */
	void onAdd(Object entity, long id);

	/**
	 * 
			* 描述:数据增加监听器
			* @param sqlBuilder
			* @author huangpengpeng2013年12月23日 下午1:30:44
	 */
	void onSave(Object entity);
	
	/**
	 * 
			* 描述:数据删除监听器
			* @param sqlBuilder
			* @author huangpengpeng2013年12月23日 下午1:30:57
	 */
	void onDelete(SqlBuilder sqlBuilder);
	
	/**
	 * 
			* 描述:数据修改监听器
			* @param sqlBuilder
			* @author huangpengpeng2013年12月23日 下午1:31:07
	 */
	void onUpdate(SqlBuilder sqlBuilder);
}
