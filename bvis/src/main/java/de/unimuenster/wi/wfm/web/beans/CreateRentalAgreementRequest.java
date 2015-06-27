package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.CustomerService;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestService;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class CreateRentalAgreementRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private TaskForm taskForm;
	
	@EJB
	private RentalAgreementRequestService rentalAgreementRequestService;
	@EJB
	private CustomerService customerService;
	
	private long selectedCustomerId;
	private String selectedRentalAgreementRequestType;
	
	private RentalAgreementRequest rentalAgreementRequest;

	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null)
			rentalAgreementRequest = new RentalAgreementRequest();
		return rentalAgreementRequest;
	}

	
	public String getSelectedRentalAgreementRequestType() {
		return selectedRentalAgreementRequestType;
	}

	public void setSelectedRentalAgreementRequestType(String selectedRentalAgreementRequestType) {
		this.selectedRentalAgreementRequestType = selectedRentalAgreementRequestType;
	}
	
	public Collection<Customer> getCustomers() {
		return customerService.getAllCustomers();
	}
	
	public long getSelectedCustomerId() {
		return selectedCustomerId;
	}

	public void setSelectedCustomerId(long id) {
		selectedCustomerId = id;
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
