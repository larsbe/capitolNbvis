package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.util.EMailSender;

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
				+ ", <br><br>";
		
		String content;		
		if(liabilityCase.getClaimCovered() == true){
			// claim covered
			content	= "We are happy to tell you that your claim is covered. <br>"
					+ "The covered amount is: " + liabilityCase.getInsuranceSum() + " EUR.";
		}else{
			// claim not covered
			content	= "We are sorry to tell you that your claim is not covered. <br>"
					+ "The reason is: " + liabilityCase.getRejectionInfo() + "<br/><br/>";
		}
		
		// add URL
		String url = (String) variables.get("reportUrl");
		if( url != null ){
			content += "<a href=\"" + url + "\" target=\"_blank\">Download Report</a>";
		}
		
		String footer = "<br><br>Sincerly BVIS";
		
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