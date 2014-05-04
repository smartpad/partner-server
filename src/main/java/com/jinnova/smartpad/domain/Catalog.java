package com.jinnova.smartpad.domain;

import java.util.List;
import com.jinnova.smartpad.partner.ICatalog;

public class Catalog {

	private final ICatalog catalog;
	
	private final List<Catalog> allSubcatalog;
	
	private List<CatalogItem> allItems;
	
	private String name;
	private String des;
	
	public Catalog(ICatalog catalog, List<Catalog> allSubcatalog) {
		this.catalog = catalog;
		this.name = catalog.getName().getName();
		this.des = catalog.getName().getDescription();
		this.allSubcatalog = allSubcatalog;
	}
	
	public ICatalog getCommonCatalog() {
		return this.catalog;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDes() {
		return des;
	}
	
	public List<Catalog> getAllSubCatalog() {
		return this.allSubcatalog;
	}
	
	public List<CatalogItem> getAllItems() {
		return this.allItems;
	}
}