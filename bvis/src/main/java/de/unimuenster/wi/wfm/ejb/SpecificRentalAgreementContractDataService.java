package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.unimuenster.wi.wfm.persistence.SpecificRentalAgreementContractData;

@Remote
public interface SpecificRentalAgreementContractDataService {
	
	public SpecificRentalAgreementContractData getSpecificRentalAgreementContractData(long id);
}