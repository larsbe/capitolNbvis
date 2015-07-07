package de.unimuenster.wi.wfm.persistence;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class LiabilityCase extends AbstractEntity {
	
	private static  final long serialVersionUID = 1L;
	
	protected CaseStatus status;
	protected boolean eligible;
	protected String claimDetails;
	protected Double insuranceSum;
	protected boolean claimCovered;
	protected String rejectionInfo;
	protected Double estimateOfCosts;
	protected String licenseNumber;

	
	@ManyToOne
	protected RentalAgreementContract rentalAgreementContract;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "liabilityCase")
	protected Collection<ImageAttachment> images = new ArrayList<ImageAttachment>();
	

	public RentalAgreementContract getRentalAgreementContract() {
		return rentalAgreementContract;
	}
	
	public void setRentalAgreementContract(RentalAgreementContract rentalAgreementContract) {
		this.rentalAgreementContract = rentalAgreementContract;
	}
	
	public boolean getEligible() {
		return eligible;
	}
	
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	
	// claim covered by capitol
	public boolean getClaimCovered() {
		return claimCovered;
	}
	
	public void setClaimCovered(boolean claimCovered) {
		this.claimCovered = claimCovered;
	}
	
	public CaseStatus getStatus() {
		return status;
	}
	
	public void setStatus(CaseStatus status) {
		this.status = status;
	}	
	
	public void setClaimDetails(String claimDetails){
		this.claimDetails = claimDetails;
	}
	
	public String getClaimDetails(){
		return claimDetails;
	}
	
	public void setInsuranceSum(Double insuranceSum){
		this.insuranceSum = insuranceSum;
	}
	
	public Double getInsuranceSum(){
		return insuranceSum;
	}
	
	public Collection<ImageAttachment> getImages() {
		return images;
	}

	public void setImages(Collection<ImageAttachment> images) {
		this.images = images;
	}

	public String toString() {
		return "LiabilityCase: ID=" + this.getId();
	}
	
	public String getRejectionInfo() {
		return rejectionInfo;
	}
	
	public void setRejectionInfo(String rejectionInfo) {
		this.rejectionInfo = rejectionInfo;
	}	
	public Double getEstimateOfCosts() {
		return estimateOfCosts;
	}
	
	public void setEstimateOfCosts(Double estimateOfCosts) {
		this.estimateOfCosts = estimateOfCosts;
	}	
	
	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}
	
	public String getLicenseNumber(){
		return licenseNumber;
	}
}
