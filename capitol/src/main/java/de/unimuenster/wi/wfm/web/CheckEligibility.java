package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Named
@ConversationScoped
public class CheckEligibility implements Serializable {

	private static final long serialVersionUID = 1L;

	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	private LiabilityCase liabilityCase;
	private long liabilityCaseId;
	
	public long getLiabilityCaseId() {
		liabilityCaseId = (Long) businessProcess.getVariable("caseID");
		return liabilityCaseId;
	}
	
	public LiabilityCase getLiabilityCase() {
		if (liabilityCase == null) {
			liabilityCase = liabilityCaseService.getLiabilityCase(getLiabilityCaseId());
			InsuranceContract contract = insuranceContractService.getInsuranceContract(liabilityCase.getInsuranceContract().getId());
			liabilityCase.setInsuranceContract(contract);
		}
		return liabilityCase;
	}
	
	public void submitForm() throws IOException {
	    // Persist updated order entity and complete task form
		businessProcess.setVariable("claimIsEligible",getLiabilityCase().getEligible());
		liabilityCase =  liabilityCaseService.mergeLiabilityCaseAndCompleteTask(getLiabilityCase());
	  }
	
}
