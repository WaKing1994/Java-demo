package com.example.demo.common.jdbc.page;

/**
 * 分页接口
 * 
 * @author hpp
 * 
 */
public interface Paginable {
	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public Integer getTotalCount();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public Integer getTotalPage();

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	public Integer getPageSize();

	/**
	 * 当前页号
	 * 
	 * @return
	 */
	public Integer getPageNo();

	/**
	 * 是否第一页
	 * 
	 * @return
	 */
	public Boolean isFirstPage();

	/**
	 * 是否最后一页
	 * 
	 * @return
	 */
	public Boolean isLastPage();

	/**
	 * 返回下页的页号
	 */
	public Integer getNextPage();

	/**
	 * 返回上页的页号
	 */
	public Integer getPrePage();
}
