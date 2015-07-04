package de.unimuenster.wi.wfm.sharedLib.data;

import java.math.BigDecimal;
import java.util.List;

public class CarData {

	private String hsntsn;
	private int amount;
	private InsuranceType insuranceType;
	private List<InsuranceBenefit> benefits;
	private BigDecimal insurancePrice;
	
	public String getHsntsn() {
		return hsntsn;
	}
	public void setHsntsn(String hsntsn) {
		this.hsntsn = hsntsn;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
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
