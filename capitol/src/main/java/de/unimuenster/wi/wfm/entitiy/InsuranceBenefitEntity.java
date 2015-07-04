package de.unimuenster.wi.wfm.entitiy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class InsuranceBenefitEntity extends AbstractEntity{
	
	private static final long serialVersionUID = 1L; 
	
	@ManyToOne
	protected InsuranceContract insuranceContract;
	
	public InsuranceContract getInsuranceContract() {
		return insuranceContract;
	}
	public void setInsuranceContract(InsuranceContract insuranceContract) {
		this.insuranceContract = insuranceContract;
	}
	
	protected InsuranceBenefit insuranceBenefit;

	public InsuranceBenefit getInsuranceBenefit() {
		return insuranceBenefit;
	}
	public void setInsuranceBenefit(InsuranceBenefit insuranceBenefit) {
		this.insuranceBenefit = insuranceBenefit;
	}
	
	
}
