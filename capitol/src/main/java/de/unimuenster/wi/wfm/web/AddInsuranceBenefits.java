package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.NegotiationCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.NegotiationCase;

@Named
@ConversationScoped
public class AddInsuranceBenefits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;

	@EJB
	private NegotiationCaseServiceBean negotiationCaseService;
	private NegotiationCase negotiationCase;
	private long NegotiationCaseId;
	
	public long getNegotiationCaseId() {
		NegotiationCaseId = (Long) businessProcess.getVariable("caseID");
		return NegotiationCaseId;
	}
	
	public NegotiationCase getNegotiationCase() {
		if (negotiationCase == null) {
			negotiationCase = negotiationCaseService.getNegotiationCase(getNegotiationCaseId());
		}
		return negotiationCase;
	}
	
	public void submitForm() {
		negotiationCase = negotiationCaseService.editNegotiationCase(negotiationCase);
		
		try {
			// Complete user task from
			//Doesn't work so far!!!!!!!!!!!!!!!!!!!!
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
	}

}
