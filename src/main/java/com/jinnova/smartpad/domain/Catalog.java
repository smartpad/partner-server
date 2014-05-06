package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.jinnova.smartpad.IPage;
import com.jinnova.smartpad.partner.ICatalog;
import com.jinnova.smartpad.partner.ICatalogField;
import com.jinnova.smartpad.partner.ICatalogItem;
import com.jinnova.smartpad.partner.IUser;

public class Catalog implements Serializable {

	//private final ICatalog catalog;

	//private User user;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4938216075669187383L;

	private List<Catalog> allSubCatalogs;
	
	private List<CatalogItem> allItems;

	private List<CatalogField> allFields;
	
	private String id;
	
	private String name;
	
	private String des;
	
	public Catalog() {
		//this.catalog = null;
		allSubCatalogs = new LinkedList<>();
		allFields = new LinkedList<>();
		allItems = new LinkedList<>();
	}
	
	public Catalog(ICatalog catalog, User user, IUser userDB) throws SQLException {
		//this.catalog = catalog;
		//this.user = user;
		this.name = catalog.getName();
		this.des = catalog.getDesc().getDescription();
		this.id = catalog.getId();
		allSubCatalogs = new LinkedList<>();
		loadAllSubCatalogs(user, userDB, catalog, allSubCatalogs);
		
		allFields = new LinkedList<>();
		loadAllCatalogField(catalog, allFields);
		
		allItems = new LinkedList<>();
		loadAllCatalogItem(userDB, catalog, allFields, allItems);
	}
	
	private static final void loadAllCatalogField(ICatalog catalog, List<CatalogField> allFields) {
		if (catalog == null) {
			return;
		}
		/*if (catalog.getCatalogSpec() == null) {
			allFields.add(new CatalogField(ICatalogField.ID_DESC, "Des", ICatalogFieldType.Text_Desc.name()));
			allFields.add(new CatalogField(ICatalogField.ID_NAME, "Name", ICatalogFieldType.Text_Name.name()));
			return;
		}*/
		ICatalogField[] allFieldRoot = catalog.getSystemCatalog().getCatalogSpec().getAllFields();
		if (allFieldRoot == null) {
			return;
		}
		for (ICatalogField cF : allFieldRoot) {
			allFields.add(new CatalogField(cF));
		}
	}

	private static final void loadAllCatalogItem(IUser user, ICatalog catalog, List<CatalogField> allFields, List<CatalogItem> allItems) throws SQLException {
		IPage<ICatalogItem> page = catalog.getCatalogItemPagingList().setPageSize(-1).loadPage(user, 1);
		if (page == null) {
			return;
		}
		ICatalogItem[] allDBItems = page.getPageEntries();
		if (allDBItems == null) {
			return;
		}
		for (ICatalogItem item : allDBItems) {
			allItems.add(new CatalogItem(item, allFields));
		}
	}

	private static final void loadAllSubCatalogs(User user, IUser userDB, ICatalog catalog, List<Catalog> allSubCatalogs) throws SQLException {
		IPage<ICatalog> page = catalog.getSubCatalogPagingList().setPageSize(-1).loadPage(userDB, 1);
		if (page == null) {
			return;
		}
		ICatalog[] subCatalogs = page.getPageEntries();
		if (subCatalogs == null) {
			return;
		}
		for (ICatalog subCatalog : subCatalogs) {
			allSubCatalogs.add(new Catalog(subCatalog, user, userDB));
		}
	}

	public String getId() {
		return id;
	}
	
	/*public ICatalog getCommonCatalog() {
		return this.catalog;
	}*/
	
	public String getName() {
		return name;
	}
	
	public String getDes() {
		return des;
	}
	
	public List<Catalog> getAllSubCatalogs() throws SQLException {
		return allSubCatalogs;
	}
	
	public List<CatalogItem> getAllItems() {
		return this.allItems;
	}
	
	public List<CatalogField> getAllFields() {
		return this.allFields;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public void setAllSubCatalogs(List<Catalog> allSubCatalogs) {
		this.allSubCatalogs = allSubCatalogs;
	}

	public void setAllItems(List<CatalogItem> allItems) {
		this.allItems = allItems;
	}

	public void setAllFields(List<CatalogField> allFields) {
		this.allFields = allFields;
	}
	
}