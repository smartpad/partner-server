package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.IPage;
import com.jinnova.smartpad.IPagingList;
import com.jinnova.smartpad.partner.ICatalog;
import com.jinnova.smartpad.partner.IOperation;
import com.jinnova.smartpad.partner.IOperationSort;
import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;

public class User implements Serializable, INeedTokenObj {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private transient IUser user;
	
	private Catalog catalog;
	
	private String userNameText;
	
	private String passwordText;

	private Branch branch;

	private List<Branch> allStoreList;

	private Token token;
	
	private Paging itemPaging;

	public User() {
	}
	
	public User(IUser user, Token token) {
		this.user = user;
		this.token = token;
		this.userNameText = user.getLogin();
	}

	public Catalog getCatalog() throws SQLException {
		//catalog = new Catalog(user.getBranch().getRootCatalog(), this, user);
		ICatalog rootCat = user.getBranch().getRootCatalog();
		catalog = new Catalog(rootCat.getSystemCatalog().getId(), rootCat, user, token);
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Branch getBranch() throws SQLException {
		this.branch = new Branch(user.getBranch(), user);
		return this.branch;
	}
	
	public List<Branch> getAllStoreList() throws SQLException {
		this.allStoreList = new LinkedList<>();
		IPagingList<IOperation, IOperationSort> listPStore = user.getStorePagingList();
		listPStore.setPageSize(-1);
		IPage<IOperation> listStore = listPStore.loadPage(user, 1);
		if (listStore.getTotalCount() <= 0) {
			return allStoreList;
		}
		for (IOperation operation : listStore.getPageEntries()) {
			this.allStoreList.add(new Branch(operation, user));
		}
		return allStoreList;
	}

	public Branch updateBranch(Branch branchToUpdate) throws SQLException {
		if (branchToUpdate == null) {
			return getBranch();
		}
		if (branchToUpdate.isNew()) {
			IOperation newStoreDB = user.getStorePagingList().newEntryInstance(user);
			Branch newBranch = new Branch(newStoreDB, user); // inherit from root branch
			branchToUpdate.updateToDB(newBranch);
			user.getStorePagingList().put(user, newBranch.toBranchDB());
			return getBranch();
		}
		if (branchToUpdate.updateToDB(this.getBranch())) {
			user.updateBranch();
			return getBranch();
		}
		for (Branch b : this.getAllStoreList()) {
			if (branchToUpdate.updateToDB(b)) {
				user.getStorePagingList().put(user, b.toBranchDB());
				return getBranch();
			}
		}
		return getBranch();
	}

	public String deleteBranch(String branchId) throws SQLException {
		if (branchId == null) {
			return "Delete invalid branch";
		}
		if (this.allStoreList == null) {
			return "Store list is empty now";
		}
		for (Branch branch : allStoreList) {
			if (branchId.equals(branch.getId())) {
				user.getStorePagingList().delete(user, branch.toBranchDB());
				return null;
			}
		}
		return "Can not find this branch to delete";
	}

	public String getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(String userNameText) {
		this.userNameText = userNameText;
	}

	public String getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(String passwordText) {
		this.passwordText = passwordText;
	}

	public Token getToken() {
		return token;
	}

	public Paging getItemPaging() {
		return itemPaging;
	}

	public Catalog updateCatalog(Catalog updateCatalog) throws SQLException {
		if (updateCatalog == null) {
			return null;
		}
		Catalog result = getCatalog().updateFromThisAndBelowCats(updateCatalog);
		if (result != null) {
			return getCatalog();
		}
		return null;
	}

	/**
	 * NOTE do not change method name like get... cause auto convert to json and make error
	 * @return user loaded from db
	 */
	@JsonIgnore
	public IUser toUserDB() {
		return this.user;
	}

	public List<Branch> getAllBranchIncludeRoot() throws SQLException {
		List<Branch> result = new LinkedList<>();
		result.add(this.getBranch());
		result.addAll(this.getAllStoreList());
		return result;
	}

	public List<CatalogItem> loadItemByPaging(String catId, Paging itemPaging) throws SQLException {
		this.itemPaging = itemPaging;
		Catalog catToLoad = getCatalogById(catId);
		if (catToLoad == null) {
			// TODO handle can not find cat
			return null;
		}
		return catToLoad.loadItem(catId, itemPaging, this.user);
	}

	public Catalog getCatalogById(String catId) throws SQLException {
		ICatalog sysCat = PartnerManager.instance.getSystemCatalog(catId);
		if (sysCat != null) {
			return new Catalog(null, sysCat, user, token);
		} else {
			return getCatalog();
		}
	}
	
	public Catalog updateCatalogItem(CatalogItem updateCatalogItem, String catalogId, String sysCatId/*, boolean sysCatalogId*/) throws SQLException {
		if (catalogId == null) {
			return null;
		}
		Catalog cat = getCatalogById(catalogId);
		if (cat == null) {
			// TODO handle can not find cat
			return null;
		}
		cat.updateItem(catalogId, sysCatId, itemPaging, updateCatalogItem, user);
		return getCatalogById(catalogId);
	}

	public Catalog deleteCatItem(String catalogItemId, String catalogId, /*boolean sysCatalogId,*/ IUser userDB) throws SQLException {
		Catalog cat = getCatalogById(catalogId);
		if (cat == null) {
			// TODO handle can not find cat
			return null;
		}
		cat.deleteCatItem(catalogId, itemPaging, catalogItemId, user);
		return getCatalogById(catalogId);
		/*if (catalogId == null) {
			return null;
		}
		ICatalog sysCatDb = PartnerManager.instance.getSystemCatalog(catalogId);
		if (sysCatDb != null) {
			new Catalog(null, sysCatDb, user, token).deleteCatItem(null, itemPaging, catalogItemId, user);
			return new Catalog(null, sysCatDb, user, token);
		} else {
			getCatalog().deleteCatItem(catalogId, itemPaging, catalogItemId, user);
			return getCatalog();
		}*/
	}

	/*public String getCatalogItemBranchNameDefault() {
		return this.user.getBranch().getRootCatalog().getCatalogItemPagingList().newEntryInstance(user).getBranchName();
	}*/

}