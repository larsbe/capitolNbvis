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
	
	public Collection<ImageAttachment> getImages() {
		return images;
	}

	public void setImages(Collection<ImageAttachment> images) {
		this.images = images;
	}

	public String toString() {
		return "LiabilityCasse: ID=" + this.getId();
	}
		
}
