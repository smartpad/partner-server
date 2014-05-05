package com.jinnova.smartpad.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jinnova.smartpad.partner.ICatalogItem;

public class CatalogItem {

	private Map<String, String> valuesSingle;
	
	private Map<String, String[]> valuesMulti;
	
	public CatalogItem() {
	}
	
	public CatalogItem(ICatalogItem item, List<CatalogField> allFields) {
		this.valuesSingle = new HashMap<>();
		this.valuesMulti = new HashMap<>();
		if (allFields != null) {
			for (CatalogField field : allFields) {
				valuesSingle.put(field.getName(), item.getFieldValue(field.getId()));
				valuesMulti.put(field.getName(), item.getFieldValues(field.getId()));
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
	
}
