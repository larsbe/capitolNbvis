package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;


@Named
@ConversationScoped
public class CalculateCarsFairValue implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	private LiabilityCase liabilityCase;
	private long liabilityCaseId;
	
	public long getLiabilityCaseId() {
		liabilityCaseId = (Long) businessProcess.getVariable("caseID");
		return liabilityCaseId;
	}
	
	public LiabilityCase getLiabilityCase() {
		if (liabilityCase == null) {
			liabilityCase = liabilityCaseService.getLiabilityCase(getLiabilityCaseId());
		}
		return liabilityCase;
	}
	
	public void submitForm() throws IOException {
	    // Persist updated order entity and complete task form

	}

}
