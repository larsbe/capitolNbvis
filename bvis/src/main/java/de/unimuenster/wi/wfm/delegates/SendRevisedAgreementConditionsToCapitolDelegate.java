package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class SendRevisedAgreementConditionsToCapitolDelegate implements JavaDelegate {

	@Inject
	RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendApprovedAgreementConditionsToCapitolDelegate");
		
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest((Long) delegateExecution.getVariable("rentalAgreementRequestId"));
		boolean conditionsApproved = rentalAgreementRequest.getNegotiationCase().getConditionsApproved();
		
		rentalAgreementMsg.setAdditionalInfo(rentalAgreementRequest.getRequirementsOfCustomer());
		
		REST.SendRevisedAgreementConditions(rentalAgreementRequest.getId(), rentalAgreementMsg, conditionsApproved);
	}

}
