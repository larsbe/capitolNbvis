package de.unimuenster.wi.wfm.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class NegotiationCase extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message="You have to enter a value for the field 'idOfOpposite'" )
	protected long idOfOpposite;	
	@NotNull(message="You have to enter a value for the field 'Date'" )
	protected Date date;	
	protected String agreementInfo;
	protected String changeComments;
	@NotNull(message="You have to enter a value for the field 'conditionsApproved'" )
	protected boolean conditionsApproved;
	
	public Long getIdOfOpposite() {
		return idOfOpposite;
	}

	public void setIdOfOpposite(Long idOfOpposite) {
		this.idOfOpposite = idOfOpposite;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAgreementInfo() {
		return agreementInfo;
	}

	public void setAgreementInfo(String agreementInfo) {
		this.agreementInfo = agreementInfo;
	}

	public String getChangeComments() {
		return changeComments;
	}

	public void setChangeComments(String changeComments) {
		this.changeComments = changeComments;
	}

	public boolean getConditionsApproved() {
		return conditionsApproved;
	}

	public void setConditionsApproved(boolean conditionsApproved) {
		this.conditionsApproved = conditionsApproved;
	}
	
	
}
