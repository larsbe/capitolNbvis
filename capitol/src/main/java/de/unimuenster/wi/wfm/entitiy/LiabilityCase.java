package de.unimuenster.wi.wfm.entitiy;

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
	protected Integer carsFairValue;
	protected Integer insuranceSum;
	protected Double estimateOfCosts;

	
	@ManyToOne
	protected InsuranceContract insuranceContract;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "liabilityCase")
	protected Collection<ImageAttachment> images = new ArrayList<ImageAttachment>();
	

	public InsuranceContract getInsuranceContract() {
		return insuranceContract;
	}
	
	public void setInsuranceContract(InsuranceContract insuranceContract) {
		this.insuranceContract = insuranceContract;
	}
	
	public boolean getEligible() {
		return eligible;
	}
	
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
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
	
	public void setCarsFairValue(Integer carsFairValue){
		this.carsFairValue = carsFairValue;
	}
	
	public Integer getCarsFairValue(){
		return carsFairValue;
	}
	
	public void setInsuranceSum(Integer insuranceSum){
		this.insuranceSum = insuranceSum;
	}
	
	public Integer getInsuranceSum(){
		return insuranceSum;
	}
	
	public Collection<ImageAttachment> getImages() {
		return images;
	}

	public void setImages(Collection<ImageAttachment> images) {
		this.images = images;
	}

	public String toString() {
		return "LiabilityCasse: ID=" + this.getId();
	}

	public Double getEstimateOfCosts() {
		return estimateOfCosts;
	}

	public void setEstimateOfCosts(Double estimateOfCosts) {
		this.estimateOfCosts = estimateOfCosts;
	}
	
	
		
}
