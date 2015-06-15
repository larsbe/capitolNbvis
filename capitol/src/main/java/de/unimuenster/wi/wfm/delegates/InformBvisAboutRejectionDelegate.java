package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.util.EmailHelper;

@Named
public class InformBvisAboutRejectionDelegate  implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		
		Map<String, Object> variables = delegateExecution.getVariables();
		LiabilityCase claim = liabilityCaseService.getLiabilityCase((Long) variables.get("caseID"));
		
		claim.setStatus(CaseStatus.REJECTED);
		liabilityCaseService.mergeLiabilityCase(claim);
		
		EmailHelper.SendMail("capitol.wfm@gmail.com", "bvis.service@gmail.com", "LiabilityCase#" + claim.getId() + " was rejected", "/EOF");
		
	}

}
