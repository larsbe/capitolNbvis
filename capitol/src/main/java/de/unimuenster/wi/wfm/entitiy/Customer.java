package de.unimuenster.wi.wfm.entitiy;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String company;
	protected String email;
	protected String phoneNumber;
	protected String address;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "customer")
	protected Collection<NegotiationCase> negotiationCases = new ArrayList<NegotiationCase>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "customer")
	protected Collection<InsuranceContract> insuranceContracts = new ArrayList<InsuranceContract>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<NegotiationCase> getNegotiationCases() {
		return negotiationCases;
	}

	public Collection<InsuranceContract> getInsuranceContracts() {
		return insuranceContracts;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
