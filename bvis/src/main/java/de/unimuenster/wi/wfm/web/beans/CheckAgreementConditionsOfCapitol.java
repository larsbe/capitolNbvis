package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestService;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class CheckAgreementConditionsOfCapitol implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private TaskForm taskForm;
	
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
		rentalAgreementRequestId = (Long) businessProcess.getVariable("rentalAgreementRequestId");
		return rentalAgreementRequestId;
	}

	
	
	public void submit() {
		try {
						
			// store entity in database	
			this.rentalAgreementRequest = rentalAgreementRequestService.merge(getRentalAgreementRequest());
			
			// complete user task form
			taskForm.completeTask();
			
			// store process variables of this process...
			// store flag "insuranceConditionsApproved"
			businessProcess.setVariable( "insuranceConditionsApproved", getRentalAgreementRequest().getNegotiationCase().isConditionsApproved() );
			
			
		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
	}	
	
}
