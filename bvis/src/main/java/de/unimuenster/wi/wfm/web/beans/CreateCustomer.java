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
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class CreateCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	@EJB
	private CustomerService customerService;

	private Customer customer = new Customer();


	public Customer getCustomer() {
		if (customer == null)
			customer = new Customer();
		return customer;
	}

	public void submit() {
		try {
			businessProcess.setVariable("individualSolutionRequested",true);
			customerService.mergeAndCompleteTask(getCustomer());
		} catch (EJBException e) {
			
			// add all validation errors
			Misc.ValidateBean(getCustomer());
		}
	}	
	
}
