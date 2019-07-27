package com.example.demo.common.jdbc;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SqlBuilder implements Serializable {

	private static final long serialVersionUID = -6908938435483339164L;

	public SqlBuilder(String sql) {
		sqlBuilder = new StringBuilder(sql);
	}

	/**
	 * 获得查询数据库记录数的Sql语句。
	 * 
	 * @return
	 */
	public String getRowCountSql() {
		String sql = sqlBuilder.toString();
		Select select = null;
		try {
			select = (Select) new CCJSqlParserManager().parse(new StringReader(
					CCJSqlParserBugUtils.getParserSql(sql)));
		} catch (JSQLParserException e) {
			return wrapProjection()
					+ sql.substring(sql.toUpperCase().indexOf("FROM"));
		}
		return wrapProjection() + wrapProjection(select);
	}

	private String wrapProjection() {
		return ROW_COUNT;
	}

	/**
	 * 去除尾部关键信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String wrapProjection(Select select) {
		StringBuffer sql = new StringBuffer();
		PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
		if (plainSelect.getFromItem() != null) {
			FromItem table = (FromItem) plainSelect.getFromItem();
			sql.append(" from ").append(table.toString()).append(" ");
		}
		List<Join> joins = plainSelect.getJoins();
		if (CollectionUtils.isNotEmpty(joins)) {
			for (Join join : joins) {
				sql.append(join.toString()).append(" ");
			}
		}
		if (plainSelect.getWhere() != null) {
			sql.append(" where ").append(
					CCJSqlParserBugUtils.getReductionSql(plainSelect.getWhere()
							.toString()));
		}
		return sql.toString();
	}

	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String Sql_FETCH = "fetch ";
	public static final String ORDER_BY = "order ";

	public SqlBuilder andIsNull(String fName) {
		this.append(" and ").append(fName).append(" is null");
		return this;
	}

	public SqlBuilder andIsNotNull(String fName) {
		this.append(" and ").append(fName).append(" is not null");
		return this;
	}

	public SqlBuilder andIn(String fName, Object[] args) {
		this.append(" and ").append(fName).append(" in (");
		for (int i = 0; i < args.length; i++) {
			append("?");
			paramList.add(args[i]);
			if (args.length - 1 != i) {
				append(",");
			}
		}
		append(")");
		return this;
	}

	public SqlBuilder andNotIn(String fName, Object[] args) {
		this.append(" and ").append(fName).append(" not in (");
		for (int i = 0; i < args.length; i++) {
			append("?");
			paramList.add(args[i]);
			if (args.length - 1 != i) {
				append(",");
			}
		}
		append(")");
		return this;
	}

	public SqlBuilder andEqualTo(String fName, Object val) {
		this.append(" and ").append(fName).append(" =?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andLike(String fName, Object val) {
		this.append(" and ").append(fName).append(" like ?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andNotLike(String fName, Object val) {
		this.append(" and ").append(fName).append(" not like ?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andNotEqualTo(String fName, Object val) {
		this.append(" and ").append(fName).append(" !=?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andGreaterThan(String fName, Object val) {
		this.append(" and ").append(fName).append(" >?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andGreaterThanOrEqualTo(String fName, Object val) {
		this.append(" and ").append(fName).append(" >=?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andLessThanOrEqualTo(String fName, Object val) {
		this.append(" and ").append(fName).append(" <=?");
		paramList.add(val);
		return this;
	}

	public SqlBuilder andBetween(String fName, Object value1, Object value2) {
		this.append(" and ").append(fName).append(" between ?")
				.append(" and ?");
		paramList.add(value1);
		paramList.add(value2);
		return this;
	}

	public SqlBuilder andNotBetween(String fName, Object value1, Object value2) {
		this.append(" and ").append(fName).append("not between ?")
				.append(" and ?");
		paramList.add(value1);
		paramList.add(value2);
		return this;
	}

	public SqlBuilder set(String name, Object value) {
		this.blank().append(",").blank().append(name).append("=?");
		paramList.add(value);
		return this;
	}

	public SqlBuilder() {
		sqlBuilder = new StringBuilder();
	}

	public SqlBuilder append(Object obj) {
		sqlBuilder.append(obj);
		return this;
	}

	public SqlBuilder insert(int offset, Object obj) {
		sqlBuilder.insert(offset, obj);
		return this;
	}

	/**
	 * 获得原始Sql语句
	 * 
	 * @return
	 */
	public String getOrigSql() {
		return sqlBuilder.append(ArrayUtils.toString(toParamArray()))
				.toString();
	}

	protected StringBuilder sqlBuilder;

	@Override
	public String toString() {
		return sqlBuilder.toString();
	}

	public static SqlBuilder instance() {
		return new SqlBuilder();
	}

	private List<Object> paramList = new ArrayList<Object>();

	public SqlBuilder setParam(Object value) {
		paramList.add(value);
		return this;
	}

	public SqlBuilder setParams(Object... values) {
		for (Object value : values) {
			paramList.add(value);
		}
		return this;
	}

	/**
	 * 判断 对象是否为空
	 * 
	 * @param value
	 * @return
	 */
	public boolean ifNotNull(Object value) {
		//如果是字符串对象
		if (value instanceof String) {
			return StringUtils.isNotBlank((String) value);
		}
		//如果是数组对象
		if (ObjectUtils.isArray(value)) {
			return value != null && !ArrayUtils.isEmpty((Object[]) value);
		}
		return value != null;
	}

	public Object[] toParamArray() {
		return paramList.toArray();
	}

	public SqlBuilder blank() {
		return append(" ");
	}
}
