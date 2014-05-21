package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.ICatalogItem;

public class CatalogItem implements Serializable, INeedTokenObj {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486063868864231425L;

	private Map<String, String> valuesSingle;
	
	private Map<String, String[]> valuesMulti;

	private Token token;

	private String branchName;

	private GPSInfo gps;

	private String id;

	@JsonIgnore
	private ICatalogItem item;

	public CatalogItem() {
	}

	public CatalogItem(ICatalogItem item, List<CatalogField> allFields, Token token) {
		this.item = item;
		this.valuesSingle = new HashMap<>();
		this.valuesMulti = new HashMap<>();
		this.token = token;
		this.branchName = item.getBranchName();
		this.gps = new GPSInfo(item.getGps());
		this.id = item.getId();
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public GPSInfo getGps() {
		return gps;
	}

	public void setGps(GPSInfo gps) {
		this.gps = gps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public IToken getToken() {
		return token;
	}

	@JsonIgnore
	public ICatalogItem toItemDB() {
		return this.item;
	}
	
	public boolean updateToDB(ICatalogItem itemLoaded) {
		if (itemLoaded == null) {
			return false;
		}
		boolean isSameId = (itemLoaded.getId() == null && this.id == null) || (this.id != null && itemLoaded.getId().equals(this.id));
		if (!isSameId) {
			return false;
		}
		itemLoaded.setBranchName(this.branchName);
		for (Entry<String, String> field : this.valuesSingle.entrySet()) {
			itemLoaded.setField(field.getKey(), field.getValue());
		}
		for (Entry<String, String[]> field : this.valuesMulti.entrySet()) {
			itemLoaded.setField(field.getKey(), field.getValue());
		}
		return true;
	}

}