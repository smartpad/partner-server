package com.jinnova.smartpad.domain;

import java.io.Serializable;

public class Paging implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1118933473836362951L;

	private static final int PAGE_RANGE_DEFAULT = 2;
	private static final int PAGE_SIZE_DEFAULT = 10;// 10, 20, 50, 100, all

	private int pageSize = PAGE_SIZE_DEFAULT;

	private int pageRange = PAGE_RANGE_DEFAULT;

	private String sort;

	private boolean ascending;

	private int pageNumber = 1;

	private int pageCount;

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

	public boolean isNonShow() {
		return this.pageSize == 0;
	}

	public int getPageRange() {
		return pageRange;
	}

	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}

	public int getActualRange() {
		return Math.min(pageCount, pageRange);
	}

	public boolean isAllowFirst() {
		return getFirstPageNumber() > 1;
	}

	public boolean isAllowLast() {
		return getLastPageNumber() < pageCount;
	}

	public int getFirstPageNumber() {
		int firstPage = pageNumber - (pageRange / 2);
		if (firstPage < 1) {
			firstPage = 1;
		}
		return firstPage;
	}

	public int getLastPageNumber() {
		int lastPage = pageNumber + (pageRange / 2);
		if (lastPage > pageRange) {
			lastPage = this.pageRange;
		}
		return lastPage;
	}

	/*private static final int[] getRangePageNumbers() {
		int[] result = new int[2];
		int firstPage = pageNumber - (pageRange / 2);
		if (firstPage < 1) {
			firstPage = 1;
		}
		int lastPage = pageNumber + (pageRange / 2);
		if (lastPage > pageRange) {
			lastPage = this.pageRange;
		}
	}*/

	void updateLoadedPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}