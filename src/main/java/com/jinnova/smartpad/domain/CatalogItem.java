package com.jinnova.smartpad.domain;

public class CatalogItem {

	private String name;
	private String des;
	
	public CatalogItem(String name, String des) {
		this.name = name;
		this.des = des;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDes() {
		return this.des;
	}
	
}
