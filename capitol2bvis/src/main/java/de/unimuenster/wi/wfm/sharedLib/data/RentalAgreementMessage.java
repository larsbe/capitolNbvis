package de.unimuenster.wi.wfm.sharedLib.data;

import java.math.BigDecimal;
import java.util.List;

/* 
 * This class is used to exchange RentalAgreement data including insurance contract data
 * between Capitol and BVIS.
 * 
 * Note that it is used for both individual and standard solutions.
 * Depending on the negotiation phase, the content varies.
 * The rentalAgreementRequestId serves as the correlation key; other state information 
 * is held by each participant individually.
 */
public class RentalAgreementMessage {

	private long rentalAgreementRequestId;
	private CustomerData customerData;
	private List<CarData> carsData;
	private String additionalInfo;
	private InsuranceType insuranceType;
	private List<InsuranceBenefit> benefits;
	private BigDecimal insurancePrice;
	
	public long getRentalAgreementRequestId() {
		return rentalAgreementRequestId;
	}
	public void setRentalAgreementRequestId(long rentalAgreementRequestId) {
		this.rentalAgreementRequestId = rentalAgreementRequestId;
	}
	public CustomerData getCustomerData() {
		return customerData;
	}
	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}
	public List<CarData> getCarsData() {
		return carsData;
	}
	public void setCarsData(List<CarData> carsData) {
		this.carsData = carsData;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
