package de.unimuenster.wi.wfm.sharedLib.data;

import java.math.BigDecimal;
import java.util.List;

public class InsuranceData {
	private InsuranceType insuranceType;
	private List<InsuranceBenefit> benefits;
	private BigDecimal insurancePrice;
	
	public InsuranceType getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}
	public List<InsuranceBenefit> getBenefits() {
		return benefits;
	}
	public void setBenefits(List<InsuranceBenefit> benefits) {
		this.benefits = benefits;
	}
	public BigDecimal getInsurancePrice() {
		return insurancePrice;
	}
	public void setInsurancePrice(BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
	}
	
	
}
