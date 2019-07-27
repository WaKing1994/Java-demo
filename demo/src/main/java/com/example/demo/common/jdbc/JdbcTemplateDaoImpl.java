package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class JdbcTemplateDaoImpl extends SimpleJdbcTemplateDao implements
		JdbcTemplateDao {

	private Logger log = LoggerFactory.getLogger(JdbcTemplateDaoImpl.class);

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		log.debug("SQL:{}", sql);
		return super.queryForList(new SqlBuilder(sql));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryForList(String sql, Class<?> clz) {
		log.debug("SQL:{}", sql);
		return (List<T>) getJdbcTemplate().query(sql,
				BeanPropertyRowMapper.newInstance(clz));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryForList(Class<?> clz, Map<String, Object> params) {
		SqlBuilder sqlBuilder = new SqlBuilder("select * from ").append(
				clz.getSimpleName()).append(" where 1=1");
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sqlBuilder.andEqualTo(entry.getKey(), entry.getValue());
		}
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		return (List<T>) queryForList(sqlBuilder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryForObject(Class<?> clz, Map<String, Object> param) {
		SqlBuilder sqlBuilder = new SqlBuilder("select * from ").append(
				clz.getSimpleName()).append(" where 1=1");
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			sqlBuilder.andEqualTo(entry.getKey(), entry.getValue());
		}
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		return (T) queryForObject(sqlBuilder, clz);
	}

	@Override
	public Map<String, Object> queryForMap(Class<?> clz,
			Map<String, Object> param) {
		SqlBuilder sqlBuilder = new SqlBuilder("select * from ").append(
				clz.getSimpleName()).append(" where 1=1");
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			sqlBuilder.andEqualTo(entry.getKey(), entry.getValue());
		}
		log.debug("SQL:{} {}", sqlBuilder.toString(), sqlBuilder.toParamArray());
		return queryForMap(sqlBuilder);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> clz) {
		return super.queryForObject(new SqlBuilder(sql), clz);
	}

	@Override
	public int update(Class<?> clz, Map<String, Object> valueMap,
			Map<String, Object> equalsMap) {
		SqlBuilder sqlBuilder = new SqlBuilder("update " + clz.getSimpleName()
				+ " set gmtModify=current_timestamp()");
		// 设置需要修改的值
		for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
			sqlBuilder.set(entry.getKey(), entry.getValue());
		}
		sqlBuilder.append(" where 1=1 ");
		// 设置修改条件
		for (Map.Entry<String, Object> entry : equalsMap.entrySet()) {
			sqlBuilder.andEqualTo(entry.getKey(), entry.getValue());
		}
		return update(sqlBuilder);
	}

	@Override
	public int delete(Class<?> clz, Map<String, Object> equalsMap) {
		SqlBuilder sqlBuilder = new SqlBuilder("delete from "
				+ clz.getSimpleName() + "  where 1=1 ");
		// 设置删除条件
		for (Map.Entry<String, Object> entry : equalsMap.entrySet()) {
			sqlBuilder.andEqualTo(entry.getKey(), entry.getValue());
		}
		return update(sqlBuilder);
	}

	@Override
	public int queryForInt(String sql) {
		return super.queryForInt(new SqlBuilder(sql));
	}

	@Override
	public int delete(Class<?> clz, Map<String, Object> gteMap,
			Map<String, Object> lteMap) {
		SqlBuilder sqlBuilder = new SqlBuilder("delete from "
				+ clz.getSimpleName() + "  where 1=1 ");
		// 大于等于条件
		for (Map.Entry<String, Object> entry : gteMap.entrySet()) {
			sqlBuilder
					.andGreaterThanOrEqualTo(entry.getKey(), entry.getValue());
		}
		// 小于等于条件
		for (Map.Entry<String, Object> entry : lteMap.entrySet()) {
			sqlBuilder.andLessThanOrEqualTo(entry.getKey(), entry.getValue());
		}
		return update(sqlBuilder);
	}

	@Override
	public Pagination getPageMap(String sql, int pageSize, int pageNo) {
		log.debug("SQL:{}", sql);
		return super.getPageMap(new SqlBuilder(sql), pageNo, pageSize);
	}
	
	@Override
	public Pagination getPage(String sql, int pageSize, int pageNo, Class<?> entity) {
		log.debug("SQL:{}", sql);
		return super.getPage(new SqlBuilder(sql), pageNo, pageSize,entity);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		return super.queryForMap(new SqlBuilder(sql));
	}

	@Override
	public <T> T queryForPrimivite(String sql, Class<T> clz) {
		return super.queryForPrimitive(new SqlBuilder(sql), clz);
	}

	@Override
	public long delete(Long id, Class<?> entityClass) {
		SqlBuilder sqlBuilder = new SqlBuilder("delete from ").append(
				entityClass.getSimpleName());
		return update(sqlBuilder.append(" where id=").append(id));
	}
}
