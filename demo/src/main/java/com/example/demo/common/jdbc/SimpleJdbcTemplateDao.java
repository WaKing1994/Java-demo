package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.page.Pagination;
import com.example.demo.common.util.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SimpleJdbcTemplateDao implements ApplicationContextAware, InitializingBean {

	private Logger log = LoggerFactory.getLogger(SimpleJdbcTemplateDao.class);

	@Autowired(required=false)
	private NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport;

	protected JdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcDaoSupport.getJdbcTemplate();
	}

	/**
	 * 查询列表
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public <T> List<T> query(SqlBuilder sqlBuilder, Class<T> entityClass) {
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		return (List<T>) getJdbcTemplate().query(sqlBuilder.toString(), sqlBuilder.toParamArray(),
				BeanPropertyRowMapper.newInstance(entityClass));
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param sqlBuilder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public Pagination getPage(SqlBuilder sqlBuilder, int pageNo, int pageSize, Class<?> entityClass) {
		int totalCount = countQueryResult(sqlBuilder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList<Object>());
			return p;
		}
		sqlBuilder.append(" limit ").append((p.getPageNo() - 1) * p.getPageSize()).append(" , ")
				.append(p.getPageSize());
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		p.setList(query(sqlBuilder, entityClass));
		return p;
	}


	public Pagination getPage(SqlBuilder sqlBuilder, int pageNo, int pageSize, Class<?> entityClass,Boolean flag) {
		int totalCount = countQueryResult(sqlBuilder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount,flag);
		if (totalCount < 1) {
			p.setList(new ArrayList<Object>());
			return p;
		}
		sqlBuilder.append(" limit ").append((p.getPageNo() - 1) * p.getPageSize()).append(" , ")
				.append(p.getPageSize());
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		p.setList(query(sqlBuilder, entityClass));
		return p;
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param sqlBuilder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public Pagination getPageMap(SqlBuilder sqlBuilder, int pageNo, int pageSize) {
		int totalCount = countQueryResult(sqlBuilder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList<Object>());
			return p;
		}
		sqlBuilder.append(" limit ").append((p.getPageNo() - 1) * p.getPageSize()).append(" , ")
				.append(p.getPageSize());
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		p.setList(queryForList(sqlBuilder));
		return p;
	}

	/**
	 * 获得Finder的记录总数
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	private int countQueryResult(SqlBuilder sqlBuilder) {
		return getJdbcTemplate().queryForObject(sqlBuilder.getRowCountSql(), sqlBuilder.toParamArray(),Integer.class);
	}

	/**
	 * 查询返回Int
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public int queryForInt(SqlBuilder sqlBuilder) {
		return getJdbcTemplate().queryForObject(sqlBuilder.toString(), sqlBuilder.toParamArray(),Integer.class);
	}

	/**
	 * 查询返回map
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(SqlBuilder sqlBuilder) {
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		return getJdbcTemplate().queryForList(sqlBuilder.toString(), sqlBuilder.toParamArray());
	}

	/**
	 * 查询唯一对象
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public <T> T queryForObject(SqlBuilder sqlBuilder, Class<T> entityClass) {
		try {
			log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
			return (T) getJdbcTemplate().queryForObject(sqlBuilder.toString(), sqlBuilder.toParamArray(),
					BeanPropertyRowMapper.newInstance(entityClass));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T queryForMap(SqlBuilder sqlBuilder) {
		try {
			log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
			return (T) getJdbcTemplate().queryForMap(sqlBuilder.toString(), sqlBuilder.toParamArray());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	/**
	 * 查询原始类型唯一对象
	 * 
	 * @param sqlBuilder
	 * @param clz
	 * @return
	 */
	public <T> T queryForPrimitive(SqlBuilder sqlBuilder, Class<T> clz) {
		try {
			log.debug("SQL:[{}] [{}]", sqlBuilder.toString(), sqlBuilder.toParamArray());
			return (T) getJdbcTemplate().queryForObject(sqlBuilder.toString(), sqlBuilder.toParamArray(), clz);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public <T> T queryForObject(Long id, Class<T> entityClass) {
		try {
			SqlBuilder sqlBuilder = new SqlBuilder("select * from ").append(NameUtils.withTableName(entityClass))
					.append(" where id=").append(id);
			log.debug("SQL:{}", sqlBuilder.toString());
			return (T) getJdbcTemplate().queryForObject(sqlBuilder.toString(), sqlBuilder.toParamArray(),
					BeanPropertyRowMapper.newInstance(entityClass));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	/**
	 * 根据对象插入数据
	 * 
	 * @param entity
	 * @return
	 */
	public long add(Object entity) {
		String catalogName = NameUtils.withCatalogName(entity.getClass());
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		if (StringUtils.isNotBlank(catalogName)) {
			jdbcInsert.withCatalogName(catalogName);
		}
		long id = jdbcInsert.withTableName(NameUtils.withTableName(entity.getClass())).usingGeneratedKeyColumns("id")
				.executeAndReturnKey(new BeanPropertySqlParameterSource(entity)).longValue();
		for (JdbcEventListener listener : listeners) {
			listener.onAdd(entity, id);
		}
		return id;
	}

	public void addAll(List<?> values) {
		if (values == null || values.isEmpty()) {
			return;
		}
		Object entity = values.get(0);
		String catalogName = NameUtils.withCatalogName(entity.getClass());
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		if (StringUtils.isNotBlank(catalogName)) {
			jdbcInsert.withCatalogName(catalogName);
		}
		jdbcInsert = jdbcInsert.withTableName(NameUtils.withTableName(entity.getClass()))
				.usingGeneratedKeyColumns("id");
		SqlParameterSource[] batchs = new BeanPropertySqlParameterSource[] {};
		for (Object value : values) {
			batchs = (SqlParameterSource[]) ArrayUtils.add(batchs, new BeanPropertySqlParameterSource(value));
		}
		jdbcInsert.executeBatch(batchs);
	}
	
	/**
	 * 
	 * 描述:数据增加 不会覆盖Id
	 * 
	 * @param entity
	 * @author huangpengpeng2013年12月23日 下午1:33:32
	 */
	public void save(Object entity) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
		insert.withTableName(entity.getClass().getSimpleName()).execute(new BeanPropertySqlParameterSource(entity));
		for (JdbcEventListener listener : listeners) {
			listener.onSave(entity);
		}
	}

	/**
	 * 修改操作
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public int update(SqlBuilder sqlBuilder) {
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		int result = getJdbcTemplate().update(sqlBuilder.toString(), sqlBuilder.toParamArray());
		for (JdbcEventListener listener : listeners) {
			listener.onUpdate(sqlBuilder);
		}
		return result;
	}

	/**
	 * 删除操作
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public int delete(SqlBuilder sqlBuilder) {
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		int result = getJdbcTemplate().update(sqlBuilder.toString(), sqlBuilder.toParamArray());
		for (JdbcEventListener listener : listeners) {
			listener.onDelete(sqlBuilder);
		}
		return result;
	}

	/**
	 * 数据库时间监听器
	 */
	protected List<JdbcEventListener> listeners = new ArrayList<JdbcEventListener>();

	@Override
	public void afterPropertiesSet() throws Exception {
		String[] beanNamesForType = context.getBeanNamesForType(JdbcEventListener.class);
		for (String name : beanNamesForType) {
			listeners.add((JdbcEventListener) context.getBean(name));
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	private ApplicationContext context;
}
