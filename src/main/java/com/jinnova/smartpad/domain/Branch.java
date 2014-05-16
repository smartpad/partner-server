package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jinnova.smartpad.partner.IOperation;
import com.jinnova.smartpad.partner.IUser;

public class Branch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1304673772750531227L;

	@JsonIgnore
	private final IOperation branch;

	private String id;
	
	private Catalog rootCatalog;

	private GPSInfo gps;

	private Schedule openHours;

	private String addressLines;

	private String desc;

	private String name;

	private String email;

	private String phone;

	private String[] memberLevels;

	public Branch() {
		this.branch = null;
	}
	
	public Branch(IOperation branch, IUser userDB) throws SQLException {
		this.branch = branch;
		this.id = branch.getId();
		this.rootCatalog = new Catalog(null, branch.getRootCatalog(), userDB, null);
		this.addressLines = branch.getAddressLines();
		this.desc = branch.getDesc().getDescription();
		this.name = branch.getName();
		this.email = branch.getEmail();
		this.gps = new GPSInfo(branch.getGps());
		this.memberLevels = branch.getMemberLevels();
		this.phone = branch.getPhone();
		this.openHours = new Schedule(branch.getOpenHours());
		// TODO this.allPromotion = this.branch.getPromotionPagingList();
		// TODO this.allMembers = this.branch.getMemberPagingList();
	}
	
	public boolean updateToDB(Branch branch) {
		if (branch == null || branch.getId() != this.id) {
			return false;
		}
		IOperation operationToUpdate = branch.branch;
		operationToUpdate.setAddressLines(addressLines);
		operationToUpdate.setEmail(email);
		operationToUpdate.setMemberLevels(memberLevels);
		operationToUpdate.setName(name);
		operationToUpdate.setPhone(phone);
		operationToUpdate.setSystemCatalogId(this.rootCatalog.getRootCatId());
		operationToUpdate.getDesc().setDescription(desc);
		if (gps != null) {
			operationToUpdate.getGps().setLatitude(new BigDecimal(gps.getLatitude()));
			operationToUpdate.getGps().setLongitude(new BigDecimal(gps.getLongitude()));
		}
		openHours.updateToDB(operationToUpdate.getOpenHours());
		return true;
	}
	
	// TODO CRUD allPromotion, allMembers
	
	public Catalog getRootCatalog() {
		return this.rootCatalog;
	}
	
	public String getAddressLines() {
		return this.addressLines;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public String getEmail() {
		return this.email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GPSInfo getGps() {
		return gps;
	}

	public void setGps(GPSInfo gps) {
		this.gps = gps;
	}

	public Schedule getOpenHours() {
		return openHours;
	}

	public void setOpenHours(Schedule openHours) {
		this.openHours = openHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getMemberLevels() {
		return memberLevels;
	}

	public void setMemberLevels(String[] memberLevels) {
		this.memberLevels = memberLevels;
	}

	public void setRootCatalog(Catalog rootCatalog) {
		this.rootCatalog = rootCatalog;
	}

	public void setAddressLines(String addressLines) {
		this.addressLines = addressLines;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isNew() {
		return this.id == null || this.id.trim().isEmpty();
	}

	@JsonIgnore
	public IOperation toBranchDB() {
		return this.branch;
	}
	
}
