package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
	private List<T> data;//当前页的数据		查询  limit (pageNumber-1)*pageSize,pageSize
	private int pageNumber;//当前页			页面传递过来
	private int totalRecord;//总条数		查询  count(*)
	private int pageSize;//每页显示的数量	固定值
	private int totalPage;//总页数			计算出来 (int)Math.ceil(totalRecord*1.0/pageSize);
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPage() {
		return (int)Math.ceil(totalRecord*1.0/pageSize);
	}
	
	/**
	 * 获取开始索引
	 * @return
	 */
	public int getStartIndex(){
		return (pageNumber-1)*pageSize;
	}
	
	public PageBean(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	
}
