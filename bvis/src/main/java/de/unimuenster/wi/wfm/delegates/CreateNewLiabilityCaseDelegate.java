package de.unimuenster.wi.wfm.delegates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.persistence.ImageAttachment;
import de.unimuenster.wi.wfm.persistence.LiabilityCase;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;

@Named
public class CreateNewLiabilityCaseDelegate implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractServiceBean;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("CreateNewLiabilityCaseDelegate");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();

		// --- get contract ---
		RentalAgreementContract contract = rentalAgreementContractServiceBean.getRentalAgreementContract((Long) variables.get("contractNoBVIS"));
		
		// --- create new liablity case ---
		LiabilityCase liabilityCase = new LiabilityCase();
		liabilityCase.setRentalAgreementContract(contract);
		
		// attach images
		int imageCount = (Integer) variables.get("imageCount");
		Collection<ImageAttachment> images = new ArrayList<ImageAttachment>();
		for(int i = 1; i<=imageCount; i++){
			ImageAttachment image = new ImageAttachment();
			image.setFilePath((String) variables.get("image_" + i));
			images.add(image);
		}
		liabilityCase.setImages(images);

		// persist liabilityCase in database
		liabilityCase = liabilityCaseService.createLiabilityCase(liabilityCase);


		// ------ store business process variables -------
		businessProcess.setVariable("claimIdBVIS", liabilityCase.getId());
	}

}