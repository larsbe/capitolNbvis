package de.unimuenster.wi.wfm.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseService;
import de.unimuenster.wi.wfm.ejb.NegotiationCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.ImageAttachment;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.entitiy.NegotiationCase;

@Named
public class CreateNegotiationCaseDelegate implements JavaDelegate {

	@EJB
	private NegotiationCaseServiceBean negotiationService;
	@EJB
	private CustomerServiceBean customerService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		// Retrieve Process Vars
		Map<String, Object> variables = delegateExecution.getVariables();
		
		// Content will be deleted at the end ...
		Map<String, Object> variablesToRemove = new HashMap<String, Object>();
		
		// Create new Case
		NegotiationCase negotiation = new NegotiationCase();
		
		Customer customer = customerService.getCustomerByName((String) variables.get("customer"));
		negotiation.setCustomer(customer);
		variablesToRemove.put("customer", variables.get("customer"));
		negotiation.setAgreementInfo((String) variables.get("agreementConditions"));
		variablesToRemove.put("agreementConditions", variables.get("agreementConditions"));
		negotiation.setConditionsApproved(false);
		
		// Store negotiation
		negotiation = negotiationService.createNegotiationCase(negotiation);
		
		// Publish ID
		delegateExecution.setVariable("caseID", negotiation.getId());
		
		// Remove process vars (not longer needed)
		delegateExecution.removeVariables(variablesToRemove.keySet());
	}

}
