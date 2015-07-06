package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
public class StoreInsuranceBenefits implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("StoreInsuranceBenefits");

		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("processVariables"))
				.mapTo(RentalAgreementMessage.class);
				
		
		// retrieve Request from DB
		rentalAgreementMsg.getRentalAgreementRequestId();
		
		
		// create and store entities
//		RentalAgreementRequest req = rentalAgreementRequestService.createObjectFromMessage(rentalAgreementMsg, customer, carsData);
		

		// store process variables of this process...
		// store flag "individualSolutionRequested"
//		businessProcess.setVariable( "individualSolutionRequested", (req.getRentalAgreementRequestType() == RentalAgreementRequestType.INDIVIDUAL));
//		// store rentalAgreementRequestId
//		businessProcess.setVariable("rentalAgreementRequestId", req.getId());
//		// store flag, that customer is on site
//		businessProcess.setVariable("isCustomerOnSite", false);
				
	}
}
