package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.page.Pagination;

import java.util.List;

public interface JdbcTemplateDaoSupport {

	public <T> List<T> query(SqlBuilder sqlBuilder) ;
	
	public Pagination getPage(SqlBuilder sqlBuilder, int pageNo, int pageSize) ;
	
	public <T> T queryForObject(SqlBuilder sqlBuilder) ;
	
	public <T> T queryForObject(long id) ;
	
	public long delete(long id) ;
	
	public long update(long id, SqlBuilder sqlBuilder) ;
	
	public <T> T queryForObject(SqlBuilder sqlBuilder, Class<T> entityClass) ;
	
	public <T> T queryForObject(Long id, Class<T> entityClass) ;
}
