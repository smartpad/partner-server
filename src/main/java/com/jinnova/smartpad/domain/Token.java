package com.jinnova.smartpad.domain;

public class Token implements IToken {

	private final String userName;
	
	private final String token;
	
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
}
