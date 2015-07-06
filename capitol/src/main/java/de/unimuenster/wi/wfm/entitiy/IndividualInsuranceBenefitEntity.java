package de.unimuenster.wi.wfm.entitiy;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class IndividualInsuranceBenefitEntity extends AbstractEntity{
	
	private static final long serialVersionUID = 1L; 
	
	@ManyToOne
	protected InsuranceContract insuranceContract;
	
	public InsuranceContract getInsuranceContract() {
		return insuranceContract;
	}
	public void setInsuranceContract(InsuranceContract insuranceContract) {
		this.insuranceContract = insuranceContract;
	}
	
	private String name;
	private BigDecimal price;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
