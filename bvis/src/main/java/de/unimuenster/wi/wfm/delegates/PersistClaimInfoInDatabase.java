package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;

@Named
public class PersistClaimInfoInDatabase implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("PersistClaimInfoInDatabase");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();

		// --- get liability case ---
		LiabilityCase liabilityCase = liabilityCaseService.getLiabilityCase((Long) variables.get("claimIdBVIS"));
		
		// --- add new information
		boolean claimCovered = (Boolean) variables.get("claimCovered");
		if(claimCovered == true){
			liabilityCase.setClaimCovered(true);
			liabilityCase.setInsuranceSum((Integer) variables.get("insuranceSum"));
		}else{
			liabilityCase.setClaimCovered(false);
			liabilityCase.setRejectionInfo((String) variables.get("rejectionInfo"));
		}
		

		// persist liabilityCase in database
		liabilityCase = liabilityCaseService.merge(liabilityCase);


		// ------ store business process variables -------
		businessProcess.setVariable("claimIdBVIS", liabilityCase.getId());
	}

}