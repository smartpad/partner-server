package com.jinnova.smartpad.domain;

import java.io.Serializable;

import com.jinnova.smartpad.partner.IUser;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private IUser user;
	
	private String userNameText;
	
	private String passwordText;
	
	public User() {
	}
	
	public User(IUser user) {
		this.user = user;
	}
	
	public String getName() {
		return user.getLogin();
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
	
}
