package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class SetUpIndividualSolutionContractContainingInsuranceBenefits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	@Inject
	private TaskForm taskForm;
	
	@EJB
	private CustomerServiceBean customerService;
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractService;

	
	private RentalAgreementRequest rentalAgreementRequest;
	private long rentalAgreementRequestId;
	private RentalAgreementContract rentalAgreementContract;
	


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

	public RentalAgreementContract getRentalAgreementContract() {
		if (rentalAgreementContract == null)
			rentalAgreementContract = new RentalAgreementContract();
		rentalAgreementContract.setDate(new Date());
		return rentalAgreementContract;
	}
	public void setRentalAgreementContract(RentalAgreementContract rentalAgreementContract) {
		this.rentalAgreementContract = rentalAgreementContract;
	}
	

	
	public void submit() {
		try {		
						
			// store entity in database
			this.rentalAgreementContract.setCustomer(getRentalAgreementContract().getCustomer());
			this.rentalAgreementContract = rentalAgreementContractService.merge(getRentalAgreementContract());
			
			getRentalAgreementRequest().setRentalAgreementContract(this.rentalAgreementContract);
			this.rentalAgreementRequest = rentalAgreementRequestService.merge(getRentalAgreementRequest());
			
			
			// complete user task form
			taskForm.completeTask();
			
			// store process variables of this process...
			// store flag "contractNoBVIS"
			businessProcess.setVariable( "contractNoBVIS", this.rentalAgreementContract.getId() );		
			
						
			
		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
		
	}
}