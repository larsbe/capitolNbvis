package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.util.LiabilityCaseReport;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class InformBvisAboutRejectionDelegate implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;

	public void execute(DelegateExecution delegateExecution) throws Exception {

		Map<String, Object> variables = delegateExecution.getVariables();
		LiabilityCase claim = liabilityCaseService
				.getLiabilityCase((Long) variables.get("caseID"));
		
		// Create PDF
		LiabilityCaseReport report = LiabilityCaseReport.CreateRejection(claim);
		String reportUrl = report.generatePDF();
		System.out.println("Rejection Report: " + reportUrl);

		// Update Claim
		claim.setStatus(CaseStatus.REJECTED);
		claim.setReportUrl(reportUrl);
		claim = liabilityCaseService.mergeLiabilityCase(claim);
		
		// Inform BVIS
		REST.SendLiabilityCaseRejectionInformation(claim);

	}

}
