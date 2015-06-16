package de.unimuenster.wi.wfm.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class RentalAgreementRequest extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@NotNull
	protected Customer customer;	
	@NotNull
	protected RentalAgreementRequestType rentalAgreementRequestType;	
	protected String requirements;


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public RentalAgreementRequestType getRentalAgreementRequestType() {
		return rentalAgreementRequestType;
	}
	
	public void setRentalAgreementRequestType(RentalAgreementRequestType rentalAgreementRequestType) {
		this.rentalAgreementRequestType = rentalAgreementRequestType;
	}	
	
	
}
