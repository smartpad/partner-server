package com.jinnova.smartpad.domain;

public class InvalidParamerFromUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8518606650136516558L;

	public InvalidParamerFromUserException() {
		super();
	}

	public InvalidParamerFromUserException(String mess) {
		super(mess);
	}

	public InvalidParamerFromUserException(String mess, Throwable t) {
		super(mess, t);
	}
}
