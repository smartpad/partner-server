package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.IPage;
import com.jinnova.smartpad.IPagingList;
import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.partner.ICatalog;
import com.jinnova.smartpad.partner.ICatalogField;
import com.jinnova.smartpad.partner.ICatalogItem;
import com.jinnova.smartpad.partner.ICatalogItemSort;
import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

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

	private String rootCatId;
	
	private String branchName;
	
	public Catalog() {
		this.catalog = null;
		allSubCatalogs = new LinkedList<>();
		allFields = new LinkedList<>();
		allItems = new LinkedList<>();
	}

	public Catalog(String parentId, ICatalog catalog, IUser userDB, Token token) throws SQLException {
		this(parentId, catalog, userDB, token, null);
	}

	public Catalog(String parentId, ICatalog catalog, IUser userDB, Token token, Paging itemPaging) throws SQLException {
		this.parentId = parentId;
		this.catalog = catalog;
		this.branchName = this.catalog.getCatalogItemPagingList().newEntryInstance(userDB).getBranchName();
		//this.user = user;
		ICatalog sysCat = this.catalog.getSystemCatalog();
		if (sysCat != null) {
			this.rootCatId = sysCat.getId();
		}
		this.name = catalog.getName();
		this.des = catalog.getDesc().getDescription();
		this.id = catalog.getId();
		this.token = token;
		allSubCatalogs = new LinkedList<>();
		loadAllSubCatalogs(userDB, catalog, allSubCatalogs, token);
		
		allFields = new LinkedList<>();
		loadAllCatalogField(catalog, allFields, token);
		
		allItems = new LinkedList<>();
		loadAllCatalogItem(userDB, catalog, null, allFields, allItems, token);
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
		ICatalogField[] allFieldRoot = null;
		ICatalog sysCat = catalog.getSystemCatalog();
		if (sysCat == null) {
			allFieldRoot = catalog.getCatalogSpec().getAllFields();
		} else {
			allFieldRoot = sysCat.getCatalogSpec().getAllFields();
		}
		if (allFieldRoot == null) {
			return;
		}
		for (ICatalogField cF : allFieldRoot) {
			allFields.add(new CatalogField(cF, token));
		}
	}

	private static final void loadAllCatalogItem(IUser user, ICatalog catalog, Paging itemPaging, List<CatalogField> allFields, List<CatalogItem> allItems, Token token) throws SQLException {
		if (allItems == null) {
			return;
		}
		allItems.clear();
		if (/*catalog.getSystemCatalog() == null || */itemPaging == null || itemPaging.isNonShow()) {
			return; // TODO BUG getCatalogItem from sysCat may cause nullpointer
		}
		IPagingList<ICatalogItem, ICatalogItemSort> paging = catalog.getCatalogItemPagingList();
		if (paging == null) {
			return;
		}
		// TODO validate itemPaging
		IPage<ICatalogItem> page = paging.setPageSize(itemPaging.getPageSize()).loadPage(user, itemPaging.getPageNumber());
		if (page == null) {
			return;
		}
		itemPaging.setPageNumber(page.getPageNumber());
		itemPaging.updateLoadedPageCount(page.getPageCount());
		ICatalogItem[] allDBItems = page.getPageEntries();
		if (allDBItems == null) {
			return;
		}
		for (ICatalogItem item : allDBItems) {
			allItems.add(new CatalogItem(item, allFields, token));
		}
	}

	private static final void loadAllSubCatalogs(IUser userDB, ICatalog catalog, List<Catalog> allSubCatalogs, Token token) throws SQLException {
		IPage<ICatalog> page = catalog.getSubCatalogPagingList().setPageSize(-1).loadPage(userDB, 1);
		if (page == null) {
			return;
		}
		ICatalog[] subCatalogs = page.getPageEntries();
		if (subCatalogs == null) {
			return;
		}
		for (ICatalog subCatalog : subCatalogs) {
			allSubCatalogs.add(new Catalog(catalog.getId(), subCatalog, userDB, token));
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
			this.catalog.getDesc().setDescription(updateCatalog.getDes());
			this.catalog.getSubCatalogPagingList().put(userDB, this.catalog);
			return this;
		}
		if (updateCatalog.isNew() && updateCatalog.getParentId().equals(this.id)) {
			ICatalog cat = this.catalog.getSubCatalogPagingList().newEntryInstance(userDB);
			cat.setName(updateCatalog.getName());
			cat.getDesc().setDescription(updateCatalog.getDes());
			this.catalog.getSubCatalogPagingList().put(userDB, cat);
			return new Catalog(this.getId(), cat, userDB, token);
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
		ICatalog sysCat = this.catalog.getSystemCatalog();
		if (sysCat == null || sysCat.getId().equals(catalogId)) {
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

	public String getRootCatId() {
		return this.rootCatId;
	}
	
	public void setRootCatId(String rootCatId) {
		this.rootCatId = rootCatId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public boolean isSysCat() {
		return PartnerManager.instance.getSystemCatalog(this.id) != null;
	}

	List<CatalogItem> loadItem(String catalogId, Paging itemPaging, IUser userDB) throws SQLException {
		if (catalogId == null || this.id.equals(catalogId)) {
			loadAllCatalogItem(userDB, catalog, itemPaging, allFields, allItems, token);
			return this.allItems;
		} else {
			for (Catalog subCat : allSubCatalogs) {
				List<CatalogItem> result = subCat.loadItem(catalogId, itemPaging, userDB);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	boolean updateItem(String catalogId, String sysCatId, Paging itemPaging, CatalogItem updateCatalogItem, IUser userDB) throws SQLException {
		if (catalogId == null || this.id.equals(catalogId)) {
			if (updateCatalogItem.getId() != null) {
				this.loadItem(null, itemPaging, userDB);
				for (CatalogItem itemLoaded : allItems) {
					ICatalogItem itemDB = itemLoaded.toItemDB();
					if (updateItemInternal(updateCatalogItem, itemDB, sysCatId)) {
						catalog.getCatalogItemPagingList().put(userDB, itemDB);
						break;
					}
				}
			} else {// add new item
				ICatalogItem newItem = catalog.getCatalogItemPagingList().newEntryInstance(userDB);
				updateItemInternal(updateCatalogItem, newItem, sysCatId);
				catalog.getCatalogItemPagingList().put(userDB, newItem);
			}
			return true;
		} else {
			for (Catalog subCat : allSubCatalogs) {
				if (subCat.updateItem(catalogId, sysCatId, itemPaging, updateCatalogItem, userDB)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean updateItemInternal(CatalogItem updateCatalogItem, ICatalogItem itemDB, String sysCatId) {
		if (!StringUtils.isEmpty(sysCatId)) {
			if (itemDB.getId() != null) {
				throw new RuntimeException("Cannot change catalog for existing item");
			}
			if (isSysCat() && !StringUtils.isEmpty(sysCatId)) {
				throw new RuntimeException("Selected another system catalog to save item");
			}
		}
		return updateCatalogItem.updateToDB(itemDB, sysCatId);
	}

	boolean deleteCatItem(String catalogId, Paging itemPaging, String catalogItemId, IUser user) throws SQLException {
		if (catalogItemId == null) {
			return false;
		}
		if (catalogId == null || this.id.equals(catalogId)) {
			this.loadItem(null, itemPaging, user);
			for (CatalogItem itemLoaded : allItems) {
				if (itemLoaded.getId().equals(catalogItemId)) {
					this.catalog.getCatalogItemPagingList().delete(user, itemLoaded.toItemDB());
					break;
				}
			}
			return true;
		} else {
			for (Catalog subCat : allSubCatalogs) {
				if (subCat.deleteCatItem(catalogId, itemPaging, catalogItemId, user)) {
					return true;
				}
			}
		}
		return false;
	}

	private static List<Catalog> newListCatFromDB(LinkedList<com.jinnova.smartpad.partner.Catalog> systemSubCatalogs, IUser userDB, Token token)
			throws SQLException {
		if (systemSubCatalogs == null) {
			return Collections.emptyList();
		}
		List<Catalog> result = new LinkedList<>();
		for (com.jinnova.smartpad.partner.Catalog sysSubCat : systemSubCatalogs) {
			result.add(new Catalog(sysSubCat.getParentCatalogId(), sysSubCat, userDB, token));
		}
		return result;
	}

	public JsonResponse getJsonResponse(IUser userDB, Token token) throws SQLException {
		JsonResponse result = new JsonResponse(true, this);
		if (!isSysCat()) {
			result.put("subSysCat", newListCatFromDB(PartnerManager.instance.getSystemSubCatalog(getParentId()), userDB, token));
		}
		return result;
	}
}