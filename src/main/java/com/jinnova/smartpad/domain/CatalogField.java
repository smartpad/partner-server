package com.jinnova.smartpad.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.ICatalogField;

public class CatalogField {
	
	@JsonIgnore
	private final ICatalogField catalogField;

	private String id;
	
	private String name;
	
	private String fieldType;
	
	public CatalogField() {
		this.catalogField = null;
	}
	
	public CatalogField(String id, String name, String fieldType) {
		this.catalogField = null;
		this.id = id;
		this.name = name;
		this.fieldType = fieldType;
	}
	
	public CatalogField(ICatalogField catalogField) {
		this.catalogField = catalogField;
		this.id = this.catalogField.getId();
		this.name = this.catalogField.getName();
		this.fieldType = this.catalogField.getFieldType().name();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFieldType() {
		return fieldType;
	}

	public void setId(String id) {
		// do nothing
	}

	public void setName(String name) {
		// do nothing
	}

	public void setFieldType(String fieldType) {
		// do nothing
	}
	
	
}
