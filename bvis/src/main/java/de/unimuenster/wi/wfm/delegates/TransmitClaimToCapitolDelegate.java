package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.persistence.ImageAttachment;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.rest.CapitolREST;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class TransmitClaimToCapitolDelegate implements JavaDelegate {

	@Inject
	LiabilityCaseServiceBean liabilityCaseServiceBean;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("TransmitClaimToCapitolDelegate");

		
		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();
		
		LiabilityCase liabilityCase = liabilityCaseServiceBean.getLiabilityCase((Long) variables.get("claimIdBVIS"));
		
		
		String[] images = new String[liabilityCase.getImages().size ()];
		
		
		int i = 0;
		for(ImageAttachment att : liabilityCase.getImages()){
			images[i] = att.getFilePath();
			i++;
		}
	
		
		String msg = CapitolREST.NewLiabilityCase(liabilityCase.getRentalAgreementContract().getRentalAgreementRequest().getId(), liabilityCase.getClaimDetails(), liabilityCase.getEstimateOfCosts(), images, liabilityCase.getLicenseNumber(), liabilityCase.getId());
		
		REST.SendMessageToCapitol(msg);

	}

}
