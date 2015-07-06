package de.unimuenster.wi.wfm.delegates;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;

@Named
public class SendAgreementConditionsToCapitolDelegate implements JavaDelegate {

	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendAgreementConditionsToCapitolDelegate");
		
		
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest((Long) businessProcess.getVariable("rentalAgreementRequestId"));
		
		
		
	}

}
