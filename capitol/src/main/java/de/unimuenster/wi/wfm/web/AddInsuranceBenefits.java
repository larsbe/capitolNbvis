package de.unimuenster.wi.wfm.web;

import static org.camunda.spin.Spin.JSON;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
@ConversationScoped
public class AddInsuranceBenefits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;

	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	private InsuranceContract contract;
	private long contractId;
	
	private InsuranceBenefit newBenefit;
	
	private RentalAgreementMessage rentalAgreementMsg;
	
	public long getContractId() {
		contractId = (Long) businessProcess.getVariable("contractId");
		return contractId;
	}
	
	private RentalAgreementMessage getRentalAgreementMsg() {
		if (rentalAgreementMsg == null) {
			rentalAgreementMsg = JSON((String)businessProcess.getVariable("agreementConditions"))
					.mapTo(RentalAgreementMessage.class);
		}
		return rentalAgreementMsg;
	}
	
	public String getAdditionalInfo() {
		return getRentalAgreementMsg().getAdditionalInfo();
	}
	
	public InsuranceContract getContract() {
		if (contract == null) {
			contract = insuranceContractService.getInsuranceContract(getContractId());
		}
		return contract;
	}
	
	public void update() {
		
	}
	
	public void submitForm() {
		
		try {
			// Complete user task from
			//Doesn't work so far!!!!!!!!!!!!!!!!!!!!
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
	}
	
	public void addBenefit() {
		try  {
			InsuranceBenefitEntity benefitEntity = new InsuranceBenefitEntity();
			benefitEntity.setInsuranceBenefit(newBenefit);
			insuranceContractService.addInsuranceBenefitEntities(contractId, benefitEntity);
		} catch (EJBException e) {
			
		}
		toPage();
	}

	public String removeFromBenefits(InsuranceBenefitEntity benefitEntity) {
		insuranceContractService.removeFromInsuranceBenefitEntities(benefitEntity);
		return toPage();
	}
	
	public InsuranceBenefit getNewBenefit() {
		return newBenefit;
	}
	
	public List<InsuranceBenefit> getAllBenefits() {
		return Arrays.asList(InsuranceBenefit.values());
	}
	
	private String toPage() {
		return "/deliveryOrder/details.xhtml?faces-redirect=true&id=" + contractId;
	}

}
