package de.unimuenster.wi.wfm.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.mail.MessagingException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseService;
import de.unimuenster.wi.wfm.ejb.CustomerService;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.CaseStatus;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.application.Util;

@Named
public class CreateNewLiabilityCaseDelegate implements JavaDelegate {

	@EJB
	private LiabilityCaseService liabilityCaseService;
	@EJB
	private CustomerService customerService;
	

	public void execute(DelegateExecution delegateExecution) throws Exception {

		System.out.println("CreateNewLiabilityCaseDelegate");

		// Retrieve Process Vars
		Map<String, Object> variables = delegateExecution.getVariables();

		// Content will be deleted at the end ...
		Map<String, Object> variablesToRemove = new HashMap<String, Object>();

		// get Customer
		Customer customer = customerService.getCustomer((Long) variables.get("customer"));
		
//		// Create new Case
//		LiabilityCase claim = new LiabilityCase();
//		claim.setCustomer(customer);
//		variablesToRemove.put("customer", variables.get("customer"));
//		claim.setStatus(CaseStatus.NEW);
//
//		// Store claim
//		claim = liabilityCaseService.createLiabilityCase(claim);
//
//		// Publish ID
//		delegateExecution.setVariable("caseID", claim.getId());
//
//		// Remove process vars (not longer needed)
//		delegateExecution.removeVariables(variablesToRemove.keySet());

		// inform Customer about received Claim
		try {
			Util.sendEmail(
					"Confirmation",
					"We received your claim and will process it as fast as possible.",
					"david.jauernig@googlemail.com");

		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
}
