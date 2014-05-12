package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.IPage;
import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.partner.ICatalog;
import com.jinnova.smartpad.partner.ICatalogField;
import com.jinnova.smartpad.partner.ICatalogItem;
import com.jinnova.smartpad.partner.IUser;

public class Catalog implements Serializable, INeedTokenObj {


	//private User user;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4938216075669187383L;
	
	@JsonIgnore
	private final ICatalog catalog;

	private List<Catalog> allSubCatalogs;
	
	private List<CatalogItem> allItems;

	private List<CatalogField> allFields;
	
	private String id;
	
	private String name;
	
	private String des;
	
	private Token token;
	
	private String parentId;
	
	public Catalog() {
		this.catalog = null;
		allSubCatalogs = new LinkedList<>();
		allFields = new LinkedList<>();
		allItems = new LinkedList<>();
	}
	
	public Catalog(String parentId, ICatalog catalog, User user, IUser userDB, Token token) throws SQLException {
		this.parentId = parentId;
		this.catalog = catalog;
		//this.user = user;
		this.name = catalog.getName();
		this.des = catalog.getDesc().getDescription();
		this.id = catalog.getId();
		this.token = token;
		allSubCatalogs = new LinkedList<>();
		loadAllSubCatalogs(user, userDB, catalog, allSubCatalogs, token);
		
		allFields = new LinkedList<>();
		loadAllCatalogField(catalog, allFields, token);
		
		allItems = new LinkedList<>();
		loadAllCatalogItem(userDB, catalog, allFields, allItems, token);
	}
	
	private static final void loadAllCatalogField(ICatalog catalog, List<CatalogField> allFields, Token token) {
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
			allFields.add(new CatalogField(cF, token));
		}
	}

	private static final void loadAllCatalogItem(IUser user, ICatalog catalog, List<CatalogField> allFields, List<CatalogItem> allItems, Token token) throws SQLException {
		IPage<ICatalogItem> page = catalog.getCatalogItemPagingList().setPageSize(-1).loadPage(user, 1);
		if (page == null) {
			return;
		}
		ICatalogItem[] allDBItems = page.getPageEntries();
		if (allDBItems == null) {
			return;
		}
		for (ICatalogItem item : allDBItems) {
			allItems.add(new CatalogItem(item, allFields, token));
		}
	}

	private static final void loadAllSubCatalogs(User user, IUser userDB, ICatalog catalog, List<Catalog> allSubCatalogs, Token token) throws SQLException {
		IPage<ICatalog> page = catalog.getSubCatalogPagingList().setPageSize(-1).loadPage(userDB, 1);
		if (page == null) {
			return;
		}
		ICatalog[] subCatalogs = page.getPageEntries();
		if (subCatalogs == null) {
			return;
		}
		for (ICatalog subCatalog : subCatalogs) {
			allSubCatalogs.add(new Catalog(catalog.getId(), subCatalog, user, userDB, token));
		}
	}

	public String getId() {
		return id;
	}
	
	/*public ICatalog getCommonCatalog() {
		return this.catalog;
	}*/
	
	public String getParentId() {
		return parentId;
	}
	
	public boolean isRootCatalog() {
		return this.parentId == null;
	}

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

	public IToken getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public boolean isNew() {
		return this.id == null || this.id.trim().isEmpty();
	}

	public Catalog updateFromThisAndBelowCats(Catalog updateCatalog) throws SQLException {
		if (updateCatalog == null) {
			return null;
		}
		User user = UserLoggedInManager.instance.getUser(this.token.getUserName());
		IUser userDB = user.toUserDB();
		if (this.id.equals(updateCatalog.getId())) {
			this.name = updateCatalog.getName();
			this.catalog.setName(updateCatalog.getName());
			// TODO open setDes for update this info this.catalog.setDes(updateCatalog.getName()); ??
			this.catalog.getSubCatalogPagingList().put(userDB, this.catalog);
			return this;
		}
		if (updateCatalog.isNew() && updateCatalog.getParentId().equals(this.id)) {
			ICatalog cat = this.catalog.getSubCatalogPagingList().newEntryInstance(userDB);
			cat.setName(updateCatalog.getName());
			this.catalog.getSubCatalogPagingList().put(userDB, cat);
			return new Catalog(this.getId(), cat, user, userDB, token);
		}
		for (Catalog catalog : allSubCatalogs) {
			Catalog result = catalog.updateFromThisAndBelowCats(updateCatalog);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public String deleteSubCatalog(String catalogId, IUser user) throws SQLException {
		if (this.catalog.getSystemCatalog().getId().equals(catalogId)) {
			return "Cannot delete root catalog";
		}
		String deleteFromSubCats = deleteSubCatalog(this.catalog, catalogId, user, this.allSubCatalogs);
		if (deleteFromSubCats != null) {
			return deleteFromSubCats;
		}
		return null;
	}

	private static String deleteSubCatalog(final ICatalog catalog, final String catalogId, final IUser user,
			List<Catalog> allSubCatalogs) throws SQLException {
		final String notFindCatalogErrorMessage = "Can not find catalog to delete";
		if (allSubCatalogs == null) {
			return notFindCatalogErrorMessage;
		}
		for (Catalog subCatalog : allSubCatalogs) {
			if (catalogId.equals(subCatalog.id)) {
				catalog.getSubCatalogPagingList().delete(user, subCatalog.catalog);
				return null;
			}
			String subCatalogResult = subCatalog.deleteSubCatalog(catalogId, user);
			if (subCatalogResult == null || !notFindCatalogErrorMessage.equals(subCatalogResult)) {
				return subCatalogResult;
			}
		}
		return notFindCatalogErrorMessage;
	}
	
}