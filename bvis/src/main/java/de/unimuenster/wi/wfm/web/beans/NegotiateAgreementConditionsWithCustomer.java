package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.CustomerService;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestService;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class NegotiateAgreementConditionsWithCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	

	private RentalAgreementRequest rentalAgreementRequest;
	
	@EJB
	private RentalAgreementRequestService rentalAgreementRequestService;
	
	private long rentalAgreementRequestId;

	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null){
			rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest(getRentalAgreementRequestId());
		}			
		return rentalAgreementRequest;
	}
	
	public long getRentalAgreementRequestId() {
		System.out.println("...access businessProcess");
		System.out.println("businessProcess: " + businessProcess);
		rentalAgreementRequestId = (Long) businessProcess.getVariable("rentalAgreementRequestId");
		System.out.println("rentalAgreementRequestId: " + rentalAgreementRequestId);
		return rentalAgreementRequestId;
	}

	
	
//	public void submit() {
//		try {
//			businessProcess.setVariable("individualSolutionRequested",true);
//			customerService.mergeAndCompleteTask(getCustomer());
//		} catch (EJBException e) {
//			
//			// add all validation errors
//			Misc.ValidateBean(getCustomer());
//		}
//	}	
	
}
