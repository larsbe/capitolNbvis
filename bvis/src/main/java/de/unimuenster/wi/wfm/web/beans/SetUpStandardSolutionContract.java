package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.CustomerService;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestService;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class SetUpStandardSolutionContract implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@EJB
	private CustomerService customerService;
	
	
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

	
	
	
	
	
	
	private String selectedStandardSolutionType;
	
	
	public String getSelectedStandardSolution() {
		return selectedStandardSolutionType;
	}

	public void setSelectedStandardSolution(String selectedStandardSolutionType) {
		this.selectedStandardSolutionType = selectedStandardSolutionType;
	}
	
	public void submit() {
		try {
			
			getRentalAgreementRequest().setCustomer(customerService.getCustomer(getSelectedCustomerId()));
			if(getSelectedRentalAgreementRequestType().equals("INDIVIDUAL")){
				getRentalAgreementRequest().setRentalAgreementRequestType(RentalAgreementRequestType.INDIVIDUAL);
			}else{
				getRentalAgreementRequest().setRentalAgreementRequestType(RentalAgreementRequestType.STANDARD);
			}
						
			// store entity in database	
			this.rentalAgreementRequest = rentalAgreementRequestService.merge(getRentalAgreementRequest());
			
			// store process variables of this process...
			// store flag "individualSolutionRequested"
			businessProcess.setVariable( "individualSolutionRequested", getRentalAgreementRequest().getRentalAgreementRequestType() == RentalAgreementRequestType.INDIVIDUAL);
			
			// store rentalAgreementRequestId
			businessProcess.setVariable("rentalAgreementRequestId", getRentalAgreementRequest().getId());
			
			// store flag, that customer is on site
			businessProcess.setVariable("isCustomerOnSite", true);
			
			
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