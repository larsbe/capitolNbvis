package de.unimuenster.wi.wfm.sharedLib.data;

import java.util.List;

public class RentalAgreementMessage {

	private long rentalAgreementRequestId;
	private CustomerData customerData;
	private List<CarData> carsData;
	private String additionalInfo;
	
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
	
}
