package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;

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
public class CheckContractIsSigned implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	@Inject
	private TaskForm taskForm;
	
	@EJB
	private CustomerServiceBean customerService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractService;

	private RentalAgreementContract rentalAgreementContract;
	
	
	
	public RentalAgreementContract getRentalAgreementContract() {
		if (rentalAgreementContract == null){
			rentalAgreementContract = rentalAgreementContractService.getRentalAgreementContract(getRentalAgreementContractId());
		}			
		return rentalAgreementContract;
	}
	
	public long getRentalAgreementContractId() {
		return (Long) businessProcess.getVariable("contractNoBVIS");
	}
	
	public void setRentalAgreementContract(RentalAgreementContract rentalAgreementContract) {
		this.rentalAgreementContract = rentalAgreementContract;
	}
	

	
	public void submit() {
		try {
			
			// store entity in database	
			this.rentalAgreementContract = rentalAgreementContractService.merge(getRentalAgreementContract());
			// complete user task form
			taskForm.completeTask();
						
			// store process variables of this process...
			// store flag "contractStatus"
			if(rentalAgreementContract.getContractSigned() == true){
				businessProcess.setVariable( "contractStatus", "contractSigned" );				
			}else{
				businessProcess.setVariable( "contractStatus", "cancelled" );
			}
			
			
			
		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementContract());
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
		
	}
}