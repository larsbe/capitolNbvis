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
	
	@ManyToOne
	protected Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "liabilityCase")
	protected Collection<ImageAttachment> images = new ArrayList<ImageAttachment>();
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean isEligible() {
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
