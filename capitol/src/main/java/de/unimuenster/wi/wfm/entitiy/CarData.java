package de.unimuenster.wi.wfm.entitiy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CarData extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	protected InsuranceContract insuranceContract;
	
	protected String hsn;
	protected String tsn;
	private String licenseNumber;
	
	public InsuranceContract getInsuranceContract() {
		return insuranceContract;
	}
	public void setInsuranceContract(InsuranceContract insuranceContract) {
		this.insuranceContract = insuranceContract;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getHsn() {
		return hsn;
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}

}
