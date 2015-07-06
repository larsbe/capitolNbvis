package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;

@ManagedBean
public class CheckClaimEligible implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private TaskForm taskForm;
	
	private RentalAgreementContract rentalAgreementContract;
	private Long  rentalAgreementContractId;
	private LiabilityCase liabilityCase;
	
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractService;
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	


	public RentalAgreementContract getRentalAgreementContract() {
		if (rentalAgreementContract == null){
			rentalAgreementContract = rentalAgreementContractService.getRentalAgreementContract(getRentalAgreementContractId());
		}			
		return rentalAgreementContract;
	}
	
	public long getRentalAgreementContractId() {
		rentalAgreementContractId = (Long) businessProcess.getVariable("contractNoBVIS");
		return rentalAgreementContractId;
	}
	
	public LiabilityCase getLiabilityCase() {
		if (liabilityCase == null){
			
			liabilityCase = liabilityCaseService.getLiabilityCase((Long) businessProcess.getVariable("claimIdBVIS"));
		}			
		return liabilityCase;
	}
	
	
	public void submit() {
		try {
			
			System.out.println(".......liabilityCase: " + getLiabilityCase());
			getLiabilityCase().setRentalAgreementContract(getRentalAgreementContract());
			
			// store new liability case in database
			this.liabilityCase = liabilityCaseService.merge(getLiabilityCase());
			
			
			// store process variables of this process...
			// store flag "claimIsEligible"
			businessProcess.setVariable( "claimIsEligible", getLiabilityCase().getEligible());
			
			// complete user task form
			taskForm.completeTask();
			
		} catch (EJBException e) {
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
	}	
	
}
