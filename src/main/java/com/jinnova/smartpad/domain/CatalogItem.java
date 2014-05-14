package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jinnova.smartpad.partner.ICatalogItem;

public class CatalogItem implements Serializable, INeedTokenObj {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486063868864231425L;

	private Map<String, String> valuesSingle;
	
	private Map<String, String[]> valuesMulti;

	private Token token;

	/*@JsonIgnore
	private ICatalogItem item;*/
	
	public CatalogItem() {
	}
	
	public CatalogItem(ICatalogItem item, List<CatalogField> allFields, Token token) {
		//this.item = item;
		this.valuesSingle = new HashMap<>();
		this.valuesMulti = new HashMap<>();
		this.token = token;
		if (allFields != null) {
			for (CatalogField field : allFields) {
				valuesSingle.put(field.getId(), item.getFieldValue(field.getId()));
				valuesMulti.put(field.getId(), item.getFieldValues(field.getId()));
			}
		}
	}
	
	public Map<String, String> getValuesSingle() {
		return this.valuesSingle;
	}
	
	public Map<String, String[]> getValuesMulti() {
		return this.valuesMulti;
	}

	public void setValuesSingle(Map<String, String> valuesSingle) {
		this.valuesSingle = valuesSingle;
	}

	public void setValuesMulti(Map<String, String[]> valuesMulti) {
		this.valuesMulti = valuesMulti;
	}

	@Override
	public IToken getToken() {
		return token;
	}
	
}
