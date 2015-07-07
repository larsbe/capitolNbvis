package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;

@Named
public class StorePdfContractDataDelegate implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("CancelContract");


		// ------ store business process variables -------
		businessProcess.setVariable("contractStatus", "cancelled");
		
		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();

		
		// --- get contract ---
		RentalAgreementContract contract = rentalAgreementContractServiceBean.getRentalAgreementContract((Long) variables.get("contractNoBVIS"));
		contract.setUrlToSignedContract((String) variables.get("contract"));
		
		rentalAgreementContractServiceBean.merge(contract);
		
	}

}