package de.unimuenster.wi.wfm.persistence;

import javax.persistence.Entity;

@Entity
public class LiabilityCase extends AbstractEntity {
	
	private static  final long serialVersionUID = 1L;
	
	protected String customer;
	protected CaseStatus status;
	protected boolean eligible;
	
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public boolean isEligible() {
		return eligible;
	}
	
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	
	public CaseStatus getStatus() {
		return status;
	}
	
	public void setStatus(CaseStatus status) {
		this.status = status;
	}	

	public String toString() {
		return "LiabilityCasse: ID=" + this.getId();
	}
		
}
