package com.example.demo.common.jdbc.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ClassName:Pagination Function: 分页
 * 
 * @author hp yellowroc@qq.com
 * @version V 1.0
 * @Date 2012 2012-5-22 上午9:54:58
 */
public class Pagination extends SimplePage implements java.io.Serializable, Paginable {

	private static final long serialVersionUID = -5538328293310199109L;

	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount) {
		super(pageNo, pageSize, totalCount);
	}
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount,Boolean flag) {
		super(pageNo, pageSize, totalCount,flag);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = (List<Object>) list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<Object> list;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	public <T> List<T> getList(Class<T> clz) {
		return (List<T>) list;
	}

	/**
	 * 获取数据对象并销毁创建一个新的对象
	 * 
	 * @return
	 */
	public <T> List<T> getListOfDestroy(Class<T> clz) {
		List<Object> old = list;
		if (list != null) {
			list = new ArrayList<Object>();
		}
		return (List<T>) old;
	}

	public Pagination add(Object addValue) {
		list.add(addValue);
		return this;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	public Pagination setList(List<?> list) {
		this.list = (List<Object>) list;
		return this;
	}
}
