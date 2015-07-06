package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.ejb.StandardAgreementTypeServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class SetUpStandardSolutionContract implements Serializable {

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
	private StandardAgreementTypeServiceBean standardAgreementTypeService;
	
	private RentalAgreementRequest rentalAgreementRequest;
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

	
	
	
	
	private StandardAgreementType selectedStandardSolutionType;
		
	public StandardAgreementType getSelectedStandardSolutionType() {
		return selectedStandardSolutionType;
	}

	public void setSelectedStandardSolutionType(StandardAgreementType selectedStandardSolutionType) {
		this.selectedStandardSolutionType = selectedStandardSolutionType;
	}
	
	public Collection<StandardAgreementType> getStandardAgreementTypes() {
		return standardAgreementTypeService.getAllStandardAgreementTypes();
	}
	
	
	public void submit() {
		try {
			
			getRentalAgreementRequest().setStandardAgreementType(getSelectedStandardSolutionType());
						
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
	
	public void refreshDropDown(){
		
	}
}