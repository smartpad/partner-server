package com.jinnova.smartpad.domain;

import java.io.Serializable;

public class Paging implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1118933473836362951L;

	private int pageSize;
	
	private String sort;
	
	private boolean ascending;
	
	private int pageNumber;
	
	public Paging() {
	}

	public Paging(int pageSize, String sort, boolean ascending, int pageNumber) {
		this.pageSize = pageSize;
		this.sort = sort;
		this.ascending = ascending;
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}