package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.VersionEntity.JdbcVisitException;
import com.example.demo.common.jdbc.page.Pagination;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public abstract class JdbcTemplateBaseDao extends SimpleJdbcTemplateDao implements JdbcTemplateDaoSupport {

	protected abstract Class<?> getEntityClass();

	@SuppressWarnings("unchecked")
	public <T> List<T> query(SqlBuilder sqlBuilder) {
		return (List<T>) query(sqlBuilder, getEntityClass());
	}

	public Pagination getPage(SqlBuilder sqlBuilder, int pageNo, int pageSize) {
		return super.getPage(sqlBuilder, pageNo, pageSize, getEntityClass());
	}
	public Pagination getPage(SqlBuilder sqlBuilder, int pageNo, int pageSize,Boolean flag) {
		return super.getPage(sqlBuilder, pageNo, pageSize, getEntityClass(),flag);
	}

	@SuppressWarnings("unchecked")
	public <T> T queryForObject(SqlBuilder sqlBuilder) {
		return (T) queryForObject(sqlBuilder, getEntityClass());
	}

	@SuppressWarnings("unchecked")
	public <T> T queryForObject(long id) {
		return (T) queryForObject(id, getEntityClass());
	}

	public long delete(long id) {
		SqlBuilder sqlBuilder = new SqlBuilder("delete from ").append(NameUtils.withTableName(getEntityClass()));
		return delete(sqlBuilder.append(" where id=").append(id));
	}

	public void updateByUpdater(Updater<?> updater) {
		Object bean = updater.getBean();
		Long identifier = null;
		String tableName = NameUtils.withTableName(bean.getClass());
		SqlBuilder sqlBuilder = new SqlBuilder("update ").append(tableName)
				.append(" set gmtModify=current_timestamp()");
		List<Field> fields = FieldUtils.getFieldsListWithAnnotation(bean.getClass(), Comment.class);
		for (Field propName : fields) {
			try {
				if (propName.getName().equalsIgnoreCase("ID")) {
					identifier = (Long) FieldUtils.readField(bean, propName.getName(), true);
					continue;
				}
				if (propName.getName().equalsIgnoreCase("gmtModify")) {
					continue;
				}
				Object value = FieldUtils.readField(bean, propName.getName(), true);
				if (!updater.isUpdate(propName.getName(), value)) {
					continue;
				}
				sqlBuilder.set(propName.getName(), value);
			} catch (Exception e) {
				throw new RuntimeException("copy property to persistent object failed: '" + propName + "'", e);
			}
		}
		update(identifier, sqlBuilder);
	}

	/**
	 * getEntityClass 对象 继承了 VersionEntity 对象 则会通过乐观锁保持数据安全
	 * 
	 * @param id
	 * @param sqlBuilder
	 * @return
	 */
	public long update(long id, SqlBuilder sqlBuilder) {
		if (getEntityClass() != null) {
			List<?> list = ClassUtils.getAllSuperclasses(getEntityClass());
			if (list.contains(VersionEntity.class)) {
				VersionEntity versionEntity = (VersionEntity) super.queryForObject(id, getEntityClass());
				long count = 0;
				if (versionEntity == null) {
					return count;
				}
				sqlBuilder.append(" , version = version+?").setParam(1).append(" where id=?").setParam(id)
						.append(" and version=?").setParam(versionEntity.getVersion());
				count = super.update(sqlBuilder);
				if (count == 0) {
					throw new JdbcVisitException();
				}
				return count;
			}
		}
		return update(sqlBuilder.append(" where id=").append(id));
	}
}
