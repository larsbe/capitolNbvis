package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.sharedLib.rest.CapitolREST;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class InformCapitolAboutContractStatusDelegate implements JavaDelegate {

	@EJB
	RentalAgreementContractServiceBean rentalAgreementContractService;
	
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("InformCapitolAboutContractStatusDelegate");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();
		RentalAgreementContract rentalAgreementContract = rentalAgreementContractService
				.getRentalAgreementContract((Long) variables
						.get("contractNoBVIS"));
		
		
		
		
		String msg = CapitolREST.ContractStatus(rentalAgreementContract.getRentalAgreementRequest().getId(), rentalAgreementContract.getContractSigned());
		
		REST.SendMessageToCapitol(msg);
		
	}

}