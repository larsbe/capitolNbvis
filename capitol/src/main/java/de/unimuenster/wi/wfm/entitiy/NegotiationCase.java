package de.unimuenster.wi.wfm.entitiy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class NegotiationCase extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	protected String agreementInfo;
	protected String changeComments;
	@NotNull
	protected boolean conditionsApproved;
	
	@ManyToOne
	protected Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAgreementInfo() {
		return agreementInfo;
	}

	public void setAgreementInfo(String agreementInfo) {
		this.agreementInfo = agreementInfo;
	}

	public String getChangeComments() {
		return changeComments;
	}

	public void setChangeComments(String changeComments) {
		this.changeComments = changeComments;
	}

	public boolean isConditionsApproved() {
		return conditionsApproved;
	}

	public void setConditionsApproved(boolean conditionsApproved) {
		this.conditionsApproved = conditionsApproved;
	}
	
	
}
