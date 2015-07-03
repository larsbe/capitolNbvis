package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Named
@ConversationScoped
public class CheckIfAdjestmentIsNeeded implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;
	
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	private LiabilityCase liabilityCase;
	private long liabilityCaseId;
	
	protected boolean adjustmentNeeded = false;
	
	public boolean getAdjustmentNeeded() {
		return adjustmentNeeded;
	}
	
	public void setAdjustmentNeeded(boolean adjustmentNeeded) {
		this.adjustmentNeeded = adjustmentNeeded;
	}
	
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
		businessProcess.setVariable("adjustmentIsNeeded",getAdjustmentNeeded());
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
