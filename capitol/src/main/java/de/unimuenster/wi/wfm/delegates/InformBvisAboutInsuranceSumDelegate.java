package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.util.LiabilityCaseReport;
import de.unimuenster.wi.wfm.util.rest.REST;

public class InformBvisAboutInsuranceSumDelegate implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;

	public void execute(DelegateExecution delegateExecution) throws Exception {

		Map<String, Object> variables = delegateExecution.getVariables();
		LiabilityCase claim = liabilityCaseService
				.getLiabilityCase((Long) variables.get("caseID"));

		// Create PDF
		LiabilityCaseReport report = LiabilityCaseReport
				.CreatePaymentReport(claim);
		String reportUrl = report.generatePDF();
		System.out.println("Payment Report: " + reportUrl);

		// Infrom BVIS
		REST.SendLiabilityCasePaymentInformation(claim);

	}

}
