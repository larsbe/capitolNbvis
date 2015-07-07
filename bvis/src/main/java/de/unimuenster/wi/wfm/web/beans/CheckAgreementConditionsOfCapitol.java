package de.unimuenster.wi.wfm.web.beans;

import static org.camunda.spin.Spin.JSON;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
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
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	private long rentalAgreementRequestId;
	private RentalAgreementMessage rentalAgreementMessage;

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

	public RentalAgreementMessage getRentalAgreementMessage() {
		if (rentalAgreementMessage == null) {
			JSON((String)businessProcess.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		}
		return rentalAgreementMessage;
	}
	
	public void submit() {
		try {
			// store process variables of this process...
			// store flag "insuranceConditionsApproved"
			businessProcess.setVariable("insuranceConditionsApproved", rentalAgreementRequest.getNegotiationCase().getConditionsApproved());
			
			// store entity in database	
			this.rentalAgreementRequest = rentalAgreementRequestService.merge(getRentalAgreementRequest());
			
			// complete user task form
			taskForm.completeTask();
			
		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
	}	
	
}
