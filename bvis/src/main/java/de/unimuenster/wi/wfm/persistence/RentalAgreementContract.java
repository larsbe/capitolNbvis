package de.unimuenster.wi.wfm.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RentalAgreementContract extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	protected Customer customer;
	protected boolean contractSigned;
	@NotNull(message="You have to enter a value for the field 'Date'" )
	protected Date date;
	protected String rentalAgreementContractData = "";
	protected String insuranceBenefits = "";
	protected String urlToSignedContract = "";
	
	@OneToOne
	protected RentalAgreementRequest rentalAgreementRequest;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "rentalAgreementContract")
	protected Collection<LiabilityCase> liabilityCases = new ArrayList<LiabilityCase>();
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public RentalAgreementRequest getRentalAgreementRequest() {
		return rentalAgreementRequest;
	}
	
	public void setRentalAgreementRequest(RentalAgreementRequest rentalAgreementRequest) {
		this.rentalAgreementRequest = rentalAgreementRequest;
	}
	
	public Collection<LiabilityCase> getLiabilityCases() {
		return liabilityCases;
	}
	
	public Boolean getContractSigned() {
		return contractSigned;
	}
	
	public void setContractSigned(Boolean contractSigned) {
		this.contractSigned = contractSigned;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRentalAgreementContractData() {
		return rentalAgreementContractData;
	}

	public void setRentalAgreementContractData(String rentalAgreementContractData) {
		this.rentalAgreementContractData = rentalAgreementContractData;
	}
	
	public String getInsuranceBenefits() {
		return insuranceBenefits;
	}

	public void setInsuranceBenefits(String insuranceBenefits) {
		this.insuranceBenefits = insuranceBenefits;
	}
	
	public String getUrlToSignedContract() {
		return urlToSignedContract;
	}

	public void setUrlToSignedContract(String urlToSignedContract) {
		this.urlToSignedContract = urlToSignedContract;
	}
	
//	getRentalAgreementRequest

}