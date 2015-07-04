package de.unimuenster.wi.wfm.entitiy;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class InsuranceContract extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	protected Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "insuranceContract")
	protected Collection<LiabilityCase> liabilityCases = new ArrayList<LiabilityCase>();
	
	protected double insuranceFee;
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Collection<LiabilityCase> getLiabilityCases() {
		return liabilityCases;
	}
	
	public double getInsuranceFee() {
		return insuranceFee;
	}
	
	public void setInsuranceFee(double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

}