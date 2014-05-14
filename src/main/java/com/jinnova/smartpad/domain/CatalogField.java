package com.jinnova.smartpad.domain;

import java.io.Serializable;

import com.jinnova.smartpad.partner.ICatalogField;

public class CatalogField implements Serializable, INeedTokenObj {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6321698794265538355L;

	/*@JsonIgnore
	private final ICatalogField catalogField;*/

	private String id;
	
	private String name;
	
	private String fieldType;

	private Token token;
	
	public CatalogField() {
		//this.catalogField = null;
	}
	
	/*public CatalogField(String id, String name, String fieldType, IToken token) {
		this.catalogField = null;
		this.id = id;
		this.name = name;
		this.fieldType = fieldType;
		this.token = token;
	}*/
	
	public CatalogField(ICatalogField catalogField, Token token) {
		//this.catalogField = catalogField;
		this.id = catalogField.getId();
		this.name = catalogField.getName();
		this.fieldType = catalogField.getFieldType().name();
		this.token = token;
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

	@Override
	public Token getToken() {
		return token;
	}

}