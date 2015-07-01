package de.unimuenster.wi.wfm.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class RentalAgreementRequest extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	
	@NotNull(message="You have to enter a value for the field 'Date'" )
	protected Date date;
	@ManyToOne
	@NotNull(message="You have to enter a value for the field 'Customer'" )
	protected Customer customer;	
	@NotNull(message="You have to enter a value for the field 'RentalAgreementRequestType'" )
	protected RentalAgreementRequestType rentalAgreementRequestType;
	protected String requirementsOfCustomer;
	protected NegotiationCase negotiationCase;
	
	// protected String agreementConditions;
	
	
	public NegotiationCase getNegotiationCase() {
		return negotiationCase;
	}
	
	public void setNegotiationCase(NegotiationCase negotiationCase) {
		this.negotiationCase = negotiationCase;
	}	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getRequirementsOfCustomer() {
		return requirementsOfCustomer;
	}

	public void setRequirementsOfCustomer(String requirementsOfCustomer) {
		this.requirementsOfCustomer = requirementsOfCustomer;
	}

	public RentalAgreementRequestType getRentalAgreementRequestType() {
		return rentalAgreementRequestType;
	}
	
	public void setRentalAgreementRequestType(RentalAgreementRequestType rentalAgreementRequestType) {
		this.rentalAgreementRequestType = rentalAgreementRequestType;
	}	
	
//	public String getAgreementConditions() {
//		return agreementConditions;
//	}
//
//	public void setAgreementConditions(String agreementConditions) {
//		this.agreementConditions = agreementConditions;
//	}
	
}
