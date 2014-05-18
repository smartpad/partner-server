package com.jinnova.smartpad.domain;

import java.io.Serializable;

public class RangeValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1650645478321504829L;
	
	private int fromValue;
	private int toValue;
	
	public RangeValue() {
	}
	
	public RangeValue(int from, int to) {
		this.fromValue = from;
		this.toValue = to;
	}

	public int getFromValue() {
		return fromValue;
	}

	public void setFromValue(int fromValue) {
		this.fromValue = fromValue;
	}

	public int getToValue() {
		return toValue;
	}

	public void setToValue(int toValue) {
		this.toValue = toValue;
	}

}