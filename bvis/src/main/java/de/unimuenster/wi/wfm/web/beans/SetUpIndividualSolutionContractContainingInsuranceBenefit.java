package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;

import de.unimuenster.wi.wfm.ejb.CustomerService;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestService;
import de.unimuenster.wi.wfm.ejb.StandardAgreementTypeService;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.SpecificRentalAgreementContractData;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class SetUpIndividualSolutionContractContainingInsuranceBenefit implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	@Inject
	private TaskForm taskForm;
	
	@EJB
	private CustomerService customerService;
	@EJB
	private RentalAgreementRequestService rentalAgreementRequestService;

	
	private RentalAgreementRequest rentalAgreementRequest;
	private long rentalAgreementRequestId;
	private SpecificRentalAgreementContractData specificRentalAgreementContractData;
	

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
	
	public SpecificRentalAgreementContractData getSpecificRentalAgreementContractData() {
		return specificRentalAgreementContractData;
	}
	public void setSpecificRentalAgreementContractData(SpecificRentalAgreementContractData specificRentalAgreementContractData) {
		this.specificRentalAgreementContractData = specificRentalAgreementContractData;
	}
	
	public void submit() {
		try {
			
			getRentalAgreementRequest().setSpecificRentalAgreementContractData(getSpecificRentalAgreementContractData());
						
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
