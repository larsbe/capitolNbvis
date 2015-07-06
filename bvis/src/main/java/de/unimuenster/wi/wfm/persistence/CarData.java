package de.unimuenster.wi.wfm.persistence;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class CarData extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	protected String name;
	protected String licenseNumber;
	protected String hsn;
	protected String tsn;
	protected String year; // Format: YYYY
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, mappedBy = "carsData")
	protected Collection<RentalAgreementRequest> rentalAgreementRequests;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
