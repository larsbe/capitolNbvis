package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class SendRevisedAgreementConditionsToCapitolDelegate implements JavaDelegate {

	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendApprovedAgreementConditionsToCapitolDelegate");
		
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		Long requestId = (Long) businessProcess.getVariable("rentalAgreementRequestIdBVIS");
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest(requestId);
		boolean conditionsApproved = (Boolean) delegateExecution.getVariable("insuranceConditionsApproved");
		
		rentalAgreementMsg.setAdditionalInfo(rentalAgreementRequest.getRequirementsOfCustomer());
		
		REST.SendRevisedAgreementConditions(rentalAgreementRequest.getId(), rentalAgreementMsg, conditionsApproved);
	}

}
