package de.unimuenster.wi.wfm.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.ImageAttachment;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.data.AgreementConditions;

import static org.camunda.spin.Spin.*;

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
		
		// Create new Case
		LiabilityCase claim = new LiabilityCase();
		
		InsuranceContract contract = insuranceContractService.getInsuranceContract((Long) variables.get("contractNo"));
		claim.setInsuranceContract(contract);
		
		variablesToRemove.put("customer", variables.get("customer"));
		claim.setStatus(CaseStatus.NEW);
		
		claim.setClaimDetails((String) variables.get("claimDetails"));
		variablesToRemove.put("claimDetails", variables.get("claimDetails"));
		
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
			
		// Test serialization
		AgreementConditions ac = new AgreementConditions();
		ObjectValue caseValue = Variables.objectValue(ac)
				   .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				   .create();	
		delegateExecution.setVariable("conditions", caseValue);
		
		// Publish ID
		delegateExecution.setVariable("caseID", claim.getId());
		
		// Remove process vars (not longer needed)
		delegateExecution.removeVariables(variablesToRemove.keySet());
	}

}

