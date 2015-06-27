package de.unimuenster.wi.wfm.sharedLib.data;

import java.util.ArrayList;
import java.util.List;

public class AgreementConditions {

	private String test;
	private List<String> wishList;
	
	public AgreementConditions() {
		test = "testString";
		wishList = new ArrayList<String>();
		wishList.add("Item 1");
		wishList.add("Item 2");
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public List<String> getWishList() {
		return wishList;
	}

	public void setWishList(List<String> wishList) {
		this.wishList = wishList;
	}
	
}
