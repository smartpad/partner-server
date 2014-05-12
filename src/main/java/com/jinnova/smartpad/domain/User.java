package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.IUser;

public class User implements Serializable, INeedTokenObj {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private transient IUser user;
	
	private Catalog catalog;
	
	private String userNameText;
	
	private String passwordText;

	private IToken token;
	
	public User() {
	}
	
	public User(IUser user, IToken token) {
		this.user = user;
		this.token = token;
		this.userNameText = user.getLogin();
	}

	public Catalog getCatalog() throws SQLException {
		//catalog = new Catalog(user.getBranch().getRootCatalog(), this, user);
		catalog = new Catalog(null, user.getBranch().getRootCatalog(), this, user, token);
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	/*public IUser getUserDB() {
		return this.user;
	}*/
	
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

	public IToken getToken() {
		return token;
	}

	public Catalog updateCatalog(Catalog updateCatalog) throws SQLException {
		if (updateCatalog == null) {
			return null;
		}
		Catalog result = getCatalog().updateFromThisAndBelowCats(updateCatalog);
		return result;
	}

	/**
	 * NOTE do not change method name like get... cause auto convert to json and make error
	 * @return user loaded from db
	 */
	@JsonIgnore
	public IUser toUserDB() {
		return this.user;
	}
}