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
	
}
