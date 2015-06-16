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
	
	@EJB
	private RentalAgreementRequestService rentalAgreementRequestService;
	@EJB
	private CustomerService customerService;
	
	private long _selectedCustomerId;
	private String _selectedRentalAgreementRequestType;
	
	private RentalAgreementRequest rentalAgreementRequest = new RentalAgreementRequest();

	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null)
			rentalAgreementRequest = new RentalAgreementRequest();
		return rentalAgreementRequest;
	}

	
	public String getSelectedRentalAgreementRequestType() {
		return _selectedRentalAgreementRequestType;
	}

	public void setSelectedRentalAgreementRequestType(String selectedRentalAgreementRequestType) {
		_selectedRentalAgreementRequestType = selectedRentalAgreementRequestType;
	}
	
	public Collection<Customer> getCustomers() {
		return customerService.getAllCustomers();
	}
	
	public long getSelectedCustomerId() {
		return _selectedCustomerId;
	}

	public void setSelectedCustomerId(long id) {
		_selectedCustomerId = id;
	}

	public void submit() {
		try {
			
			getRentalAgreementRequest().setCustomer(customerService.getCustomer(getSelectedCustomerId()));
			if(getSelectedRentalAgreementRequestType().equals("INDIVIDUAL")){
				getRentalAgreementRequest().setRentalAgreementRequestType(RentalAgreementRequestType.INDIVIDUAL);
			}else{
				getRentalAgreementRequest().setRentalAgreementRequestType(RentalAgreementRequestType.STANDARD);
			}
						
			businessProcess.setVariable( "individualSolutionRequested", getRentalAgreementRequest().getRentalAgreementRequestType() == RentalAgreementRequestType.INDIVIDUAL);
			rentalAgreementRequestService.mergeAndCompleteTask(getRentalAgreementRequest());
			
		} catch (EJBException e) {

			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());
		}
	}

}
