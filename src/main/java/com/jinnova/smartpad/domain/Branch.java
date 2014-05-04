package com.jinnova.smartpad.domain;

import java.util.List;

import com.jinnova.smartpad.partner.IOperation;

public class Branch {

	private final IOperation branch;

	private List<Catalog> catalogList;
	
	private List<Branch> subBranchList;
	
	Branch(IOperation branch, List<Branch> subBranchList) {
		this.branch = branch;
		this.subBranchList = subBranchList;
		// TODO load all Catalog
	}
}
