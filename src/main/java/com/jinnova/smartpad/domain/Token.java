package com.jinnova.smartpad.domain;

import java.io.Serializable;

public class Token implements Serializable, IToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7183777667118632389L;

	private String userName;
	
	private String token;
	
	public Token() {
	}

	public Token(String userName, String token) {
		this.userName = userName;
		this.token = token;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getToken() {
		return token;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
