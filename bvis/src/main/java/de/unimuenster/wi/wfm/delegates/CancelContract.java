package de.unimuenster.wi.wfm.delegates;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;

@Named
public class CancelContract implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("CancelContract");


		// ------ store business process variables -------
		businessProcess.setVariable("contractStatus}", "cancelled");
	}

}