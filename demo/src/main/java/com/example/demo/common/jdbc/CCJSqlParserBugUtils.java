package com.example.demo.common.jdbc;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;

/**
 * 
 <ul>
 * <li>CCJSqlParserManager 有bug 对 \' 字符不能解析</li>
 * <li>程序对用户传入的参数会经过过滤</li>
 * <li>将'字符串替换成\'</li>
 * </ul>
 * 
 * @author huangpengpeng
 * @version 1.0
 * @since 2013年12月14日 上午11:20:08
 */
public class CCJSqlParserBugUtils {

	/**
	 * 
	 * 描述:
	 * <ul>
	 * <li>CCJSqlParserManager 有bug 对 \' 字符不能解析</li>
	 * <li>程序对用户传入的参数会经过过滤</li>
	 * <li>将'字符串替换成\'</li>
	 * </ul>
	 * 
	 * @param sql
	 * @return
	 * @author huangpengpeng2013年12月14日 上午11:07:52
	 */
	public static String getParserSql(String sql) {
		String sqlValue = sql;
		if (sqlValue.contains("\\'")) {
			sqlValue = sqlValue.replace("\\'", CCJSQL_D);
		}
		if (sqlValue.contains("now()")) {
			sqlValue = sqlValue.replace("now()", CCJSQL_NOW);
		}
		return sqlValue;
	}

	/**
	 * 
	 * 描述:将替换的算起来进行换货
	 * 
	 * @param sql
	 * @return
	 * @author huangpengpeng2013年12月14日 上午11:22:29
	 */
	public static String getReductionSql(String sql) {
		String sqlValue = sql;
		if (sqlValue.contains(CCJSQL_D)) {
			sqlValue = sqlValue.replace(CCJSQL_D, "\\'");
		}
		if (sqlValue.contains(CCJSQL_NOW)) {
			sqlValue = sqlValue.replace(CCJSQL_NOW, "now()");
		}
		return sqlValue;
	}

	/**
	 * 
	 * 描述:根据sql获取表明
	 * 
	 * @param sql
	 * @return
	 * @throws JSQLParserException
	 * @author huangpengpeng2013年12月24日 上午9:22:12
	 */
	public static String getEntrie(String sql) throws JSQLParserException {
		Object obj = new CCJSqlParserManager().parse(new StringReader(
				getParserSql(sql)));
		if (obj instanceof Update) {
			Update update = (Update) obj;
			return update.getTable().getName();
		}
		if (obj instanceof Delete) {
			Delete delete = (Delete) obj;
			return delete.getTable().getName();
		} else if (obj instanceof Select) {
			Select select = (Select) obj;
			PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
			if (plainSelect.getJoins() == null
					|| plainSelect.getJoins().size() == 0) {
				return plainSelect.getFromItem().toString();
			}
		}
		throw new IllegalStateException(" table join is not null");
	}

	private static final String CCJSQL_D = "CCJSql_D";
	private static final String CCJSQL_NOW = "CCJSql_NOW";
}
