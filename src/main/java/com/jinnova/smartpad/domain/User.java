package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.sql.SQLException;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.IUser;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private IUser user;
	
	private Catalog catalog;
	
	private String userNameText;
	
	private String passwordText;
	
	public User() {
	}
	
	public User(IUser user) {
		this.user = user;
		this.userNameText = user.getLogin();
	}
	
	public Catalog getCatalog() throws SQLException {
		catalog = new Catalog(user.getBranch().getRootCatalog(), this, user);
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
	
	
}