package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;

@Named
public class StoreContractPdfData implements JavaDelegate {

	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("PersistClaimInfoInDatabase");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();

		// --- get contract id ---
		RentalAgreementContract contract = rentalAgreementContractServiceBean.getRentalAgreementContract((Long) variables.get("contractNoBVIS"));
		contract.setUrlToSignedContract((String) variables.get("contract"));
		

		// persist liabilityCase in database
		contract = rentalAgreementContractServiceBean.merge(contract);

	}
}