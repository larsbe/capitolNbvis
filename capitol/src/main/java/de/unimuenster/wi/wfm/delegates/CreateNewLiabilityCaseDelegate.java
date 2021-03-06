package de.unimuenster.wi.wfm.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.ImageAttachment;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Named
public class CreateNewLiabilityCaseDelegate implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		
		// Retrieve Process Vars
		Map<String, Object> variables = delegateExecution.getVariables();
		
		// Content will be deleted at the end ...
		Map<String, Object> variablesToRemove = new HashMap<String, Object>();
		
		// Load Contract (contractNo is provided)
		//InsuranceContract contract = insuranceContractService.getInsuranceContract((Long) variables.get("contractNo"));
		
		InsuranceContract contract = insuranceContractService.getInsuranceContractByBVISid((Long) variables.get("contractNo"));

		
		// Create new Case
		LiabilityCase claim = new LiabilityCase();
		claim.setStatus(CaseStatus.NEW);
		claim.setInsuranceContract(contract);
		
		// BVIS Case ID (Correlation Key) 
		Long bvisCaseID = (Long) variables.get("bvisCaseID");
		if(bvisCaseID == null)
			claim.setBvisCaseID(0L);
		else
			claim.setBvisCaseID(bvisCaseID);
		
		
		claim.setClaimDetails((String) variables.get("claimDetails"));
		variablesToRemove.put("claimDetails", variables.get("claimDetails"));
		
		claim.setEstimateOfCosts((Double) variables.get("estimateOfCosts"));
		variablesToRemove.put("estimateOfCosts", variables.get("estimateOfCosts"));
		
		claim.setLicenseNumber((String) variables.get("licenseNumber"));
		variablesToRemove.put("licenseNumber", variables.get("licenseNumber"));
		
		// Process Images
		Integer imageCount = (Integer) variables.get("imageCount");
		variablesToRemove.put("imageCount", variables.get("imageCount"));
		if(imageCount > 0) {
			for(int i = 1; i <= imageCount; i++) {
				String varKey = "image_" + i;
				ImageAttachment img = new ImageAttachment();
				img.setFilePath((String) variables.get(varKey));
				img.setDescription("Image " + i);
				// relation
				img.setLiabilityCase(claim);
				claim.getImages().add(img);
				// delete var
				variablesToRemove.put(varKey, variables.get(varKey));
			}
			// store var -> used for decision
			delegateExecution.setVariable("hasImages", true);
		}
		
		// Store claim
		claim = liabilityCaseService.createLiabilityCase(claim);
		
		// Publish ID
		delegateExecution.setVariable("caseID", claim.getId());
		
		// Remove process vars (not longer needed)
		delegateExecution.removeVariables(variablesToRemove.keySet());
	
	}

}

