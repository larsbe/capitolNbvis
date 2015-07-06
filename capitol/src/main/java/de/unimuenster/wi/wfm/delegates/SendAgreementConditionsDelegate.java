package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.entitiy.IndividualInsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class SendAgreementConditionsDelegate implements JavaDelegate {

	@EJB
	private InsuranceContractServiceBean insuranceContractService;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		int contractId = Integer.parseInt(delegateExecution.getVariable("contractId").toString());
		InsuranceContract contract = insuranceContractService.getInsuranceContract(contractId);
		
		//update RentalAgreementMessage
		rentalAgreementMsg.setInsurancePrice(contract.getInsurancePrice());
		rentalAgreementMsg.setInsuranceType(contract.getInsuranceType());
		if (contract.getInsuranceBenefitEntity() != null) {
			rentalAgreementMsg.setBenefits(new ArrayList<InsuranceBenefit>());
			for (InsuranceBenefitEntity benefit : contract.getInsuranceBenefitEntity()) {
				rentalAgreementMsg.getBenefits().add(benefit.getInsuranceBenefit());
			}
		}
		if (contract.getIndividualInsuranceBenefitEntity() != null) {
			rentalAgreementMsg.setIndividualBenefits(new ArrayList<String>());
			for (IndividualInsuranceBenefitEntity benefit : contract.getIndividualInsuranceBenefitEntity()) {
				rentalAgreementMsg.getIndividualBenefits().add(benefit.getName());
			}
		}
		
		//send it to BVIS
		REST.SendAgreementConditionsWithInsurance(rentalAgreementMsg);
	}
}
