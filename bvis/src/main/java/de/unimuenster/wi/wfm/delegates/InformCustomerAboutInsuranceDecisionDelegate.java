package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.application.EMailSender;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;

@Named
public class InformCustomerAboutInsuranceDecisionDelegate implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("InformCustomerAboutInsuranceDecisionDelegate");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();

		// --- get liability case ---
		LiabilityCase liabilityCase = liabilityCaseService.getLiabilityCase((Long) variables.get("claimIdBVIS"));
		
		
		// build email text		
		String salutation = "Dear "+ liabilityCase.getRentalAgreementContract().getCustomer().getName()
				+ ", \r\n\r\n";
		
		String content;		
		if(liabilityCase.getClaimCovered() == true){
			// claim covered
			content	= "We are happy to tell you that your claim is covered. \r\n"
					+ "The covered amount is: " + liabilityCase.getInsuranceSum() + "€.";
		}else{
			// claim not covered
			content	= "We are sorry to tell you that your claim is not covered. \r\n"
					+ "The reason is: " + liabilityCase.getRejectionInfo();
		}
		
		String footer = "Sincerly BVIS";
		
		String emailContent = salutation + content + footer;
		
		// send Email

		System.out.println("Send Email...");

		EMailSender.sendEmail("Liability Case " + liabilityCase.getId(),
						emailContent,						
						liabilityCase.getRentalAgreementContract().getCustomer().getEmail(), 
						null);

		System.out.println("...EMail sent");
	}
}