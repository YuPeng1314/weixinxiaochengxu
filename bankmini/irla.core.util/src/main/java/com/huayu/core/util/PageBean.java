package com.huayu.core.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页控件
 * 
 * @author ao.chen
 *
 * @param <T>
 */
public class PageBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1983948688143102148L;
	/**
	 * 当前页
	 */
	private Integer currentPage = 1;
	/**
	 * 每页显示条数
	 */
	private Integer pageSize = 10;
	/**
	 * 总页数
	 */
	private Integer totalPages;
	/**
	 * 总条数
	 */
	private Integer totalCount;
	/**
	 * 分页内容
	 */
	private List<T> list = Collections.emptyList();
	
	/**
	 * 分页按钮数量
	 */
	private Integer pageNumber = 10;
	/**
	 * 尾页按钮数量
	 */
	private Integer pageEnd = 8;

	public PageBean() {
	}

	public PageBean(Integer pageSize) {
		this.pageSize = pageSize;
	}

	// 初始化PageBean
	private void init() {
		totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
		if (currentPage < 1)
			currentPage = 1;
		if (currentPage > totalPages && totalPages !=0)
			currentPage = totalPages;
	}

	// 返回起始查询标记
	public Integer getFirstResult() {
		return (currentPage - 1) * pageSize;
	}

	// 返回终止查询标记
	public Integer getMaxResult() {
		return this.pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if(currentPage == null || currentPage == 0){
			this.currentPage = 1;
		}else{
			this.currentPage = currentPage;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		init();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}
	
	
	
	

}
