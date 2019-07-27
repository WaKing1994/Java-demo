package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.page.Pagination;

import java.util.List;
import java.util.Map;

public interface JdbcTemplateDao {
	
	long delete(Long id, Class<?> entityClass);

	<T> T queryForObject(Long id, Class<T> entityClass);

	/**
	 * 根据sql查询返回map
	 *
	 * @return
	 */
	List<Map<String, Object>> queryForList(String sql);

	Map<String, Object> queryForMap(String sql);

	<T> T queryForObject(String sql, Class<T> clz);

	/**
	 * 执行查询返回对象数组
	 *
	 * @param sql
	 * @param clz
	 * @return
	 */
	<T> List<T> queryForList(String sql, Class<?> clz);

	/**
	 *
	 * @param tableName
	 *            表明
	 * @param maps
	 *            查询参数
	 * @return
	 */
	<T> List<T> queryForList(Class<?> clz, Map<String, Object> param);

	/**
	 * sql分页查询
	 *
	 * @param sql
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Pagination getPageMap(String sql, int pageSize, int pageNo);

	Pagination getPage(String sql, int pageSize, int pageNo, Class<?> entity);

	/**
	 * sql map 分页
	 * @param sqlBuilder
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Pagination getPageMap(SqlBuilder sqlBuilder, int pageSize, int pageNo);

	/**
	 *
	 * @param tableName
	 *            表明
	 * @param maps
	 *            查询参数
	 * @return
	 */
	<T> T queryForObject(Class<?> clz, Map<String, Object> param);

	/**
	 * 查询唯一对象
	 *
	 * @param clz
	 * @param param
	 * @return
	 */
	Map<String, Object> queryForMap(Class<?> clz, Map<String, Object> param);

	/**
	 * 根据对象插入数据
	 *
	 * @param entity
	 */
	long add(Object entity);

	void save(Object object);

	/**
	 * 修改参数
	 *
	 * @param clz
	 * @param valueMap
	 * @param conditionMap
	 * @return
	 */
	int update(Class<?> clz, Map<String, Object> valueMap,
               Map<String, Object> equalsMap);

	/**
	 * 根据条件删除数据
	 *
	 * @param clz
	 * @param conditionMap
	 * @return
	 */
	int delete(Class<?> clz, Map<String, Object> equalsMap);

	/**
	 * 查询返回int
	 *
	 * @param sql
	 * @return
	 */
	int queryForInt(String sql);

	<T> T queryForPrimivite(String sql, Class<T> clz);

	/**
	 * 根据范围条件删除数据
	 *
	 * @param clz
	 * @param gteMap
	 * @param lteMap
	 * @return
	 */
	int delete(Class<?> clz, Map<String, Object> gteMap,
               Map<String, Object> lteMap);
	
	int update(SqlBuilder sqlBuilder);
}
