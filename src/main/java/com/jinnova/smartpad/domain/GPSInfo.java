package com.jinnova.smartpad.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.IGPSInfo;

public class GPSInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4425156900356392353L;

	@JsonIgnore
	private IGPSInfo gpsInfo;
	
	private long latitude;

	private long longitude;
	
	public GPSInfo() {
	}
	
	public GPSInfo(IGPSInfo gps) {
		this.gpsInfo = gps;
		this.latitude = this.gpsInfo.getLatitude().longValue();
		this.longitude = this.gpsInfo.getLongitude().longValue();
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

}