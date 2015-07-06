package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.sharedLib.rest.BvisREST;
import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class InformCapitolAboutContractStatusDelegate implements JavaDelegate {

	@EJB
	RentalAgreementContractServiceBean rentalAgreementContractService;
	
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("InformCapitolAboutContractStatusDelegate");

		
		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();
		
//		RentalAgreementContract contract = rentalAgreementContractService.getRentalAgreementContract(Long) variables.get("claimIdBVIS"));
		
		
//		String msg = BvisREST.LiabilityCaseRejectionInformation(
//				"correlationKey", claim.getReportUrl());
//		REST.SendMessageToCapitol(msg);

	}

}