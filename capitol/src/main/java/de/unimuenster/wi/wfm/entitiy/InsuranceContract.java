package de.unimuenster.wi.wfm.entitiy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class InsuranceContract extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	protected Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "insuranceContract")
	protected Collection<CarData> cardatas = new ArrayList<CarData>();

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "insuranceContract")
	protected Collection<LiabilityCase> liabilityCases = new ArrayList<LiabilityCase>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "insuranceContract")
	protected Collection<InsuranceBenefitEntity> insuranceBenefitEntity = new ArrayList<InsuranceBenefitEntity>();
	
	private String additionalInfo;
	private InsuranceType insuranceType;
	
	private BigDecimal insurancePrice;
	
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

	public BigDecimal getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Collection<LiabilityCase> getLiabilityCases() {
		return liabilityCases;
	}

	public Collection<CarData> getCardatas() {
		return cardatas;
	}

	public void setCardatas(Collection<CarData> cardatas) {
		this.cardatas = cardatas;
	}

	public Collection<InsuranceBenefitEntity> getInsuranceBenefitEntity() {
		return insuranceBenefitEntity;
	}

	public void setInsuranceBenefitEntity(
			Collection<InsuranceBenefitEntity> insuranceBenefitEntity) {
		this.insuranceBenefitEntity = insuranceBenefitEntity;
	}

	public void setLiabilityCases(Collection<LiabilityCase> liabilityCases) {
		this.liabilityCases = liabilityCases;
	}
	

}